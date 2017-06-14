package it.tecla.notescalendarexport.api.domino;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.tecla.notescalendarexport.config.ApplicationConfig;

public class DominoRestCalendarApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DominoRestCalendarApi.class);
	private static final java.util.logging.Logger JUL_LOGGER = java.util.logging.Logger.getLogger(DominoRestCalendarApi.class.getName());
	
	private static final int DEFAULT_COUNT = 100;
	
	private ApplicationConfig config = ApplicationConfig.build();
	
	public List<DominoRestCalendarEvent> getCalendarEntries(Date since, Date before) throws JsonParseException, JsonMappingException, IOException {
		
		List<DominoRestCalendarEvent> events = new ArrayList<DominoRestCalendarEvent>();
		
		DominoRestCalendarResponse response = null;
		int start = 0;
		
		do {
			
			response = getCalendarEntries(since, before, DEFAULT_COUNT, start);
			start += response.getEvents().size();
			
			events.addAll(response.getEvents());
			
		} while (!response.getEvents().isEmpty());
		
		return events;
		
	}

	public DominoRestCalendarResponse getCalendarEntries(Date since, Date before, int count, int start) throws JsonParseException, JsonMappingException, IOException {
		
		String calendarUrl = config.getDominoRestCalendarUrl();
		
		Feature loggingFeature = new LoggingFeature(JUL_LOGGER, Level.FINEST, null, null);
		Feature authenticationFeature = HttpAuthenticationFeature.basic(config.getDominoRestCalendarUsername(), config.getDominoRestCalendarPassword());

		Client restClient = ClientBuilder.newBuilder()
				.register(loggingFeature)
				.register(authenticationFeature)
				.build();
		
		String sinceString = DateFormatUtils.formatUTC(since, "yyyy-MM-dd'T'HH:mm:ss'Z'");
		String beforeString = DateFormatUtils.formatUTC(before, "yyyy-MM-dd'T'HH:mm:ss'Z'");
		LOGGER.debug("sinceString: {}", sinceString);
		LOGGER.debug("beforeString: {}", beforeString);
		
		URI uri = UriBuilder.fromUri(calendarUrl)
				.queryParam("since", sinceString)
				.queryParam("before", beforeString)
				.queryParam("count", count)
				.queryParam("start", start)
				.build();
		LOGGER.debug("uri: {}", uri);
		
		Response clientResponse = restClient.target(uri).request().get();
		
		Family respnseFamily = clientResponse.getStatusInfo().getFamily();
		LOGGER.debug("response status: {} {}", clientResponse.getStatus(), respnseFamily);
		LOGGER.debug("response type: {}", clientResponse.getMediaType());
		
		if (!respnseFamily.equals(Response.Status.Family.SUCCESSFUL)) {
			String msg = (String) clientResponse.readEntity(String.class);
			throw new RuntimeException("error " + clientResponse.getStatus() + " " +  respnseFamily + " while reading inotes calendar feed " + uri + ": " + msg);
		}
		
		String response = clientResponse.readEntity(String.class);
		LOGGER.debug("response: {}", response);
		
		DominoRestCalendarResponse restCalendarResponse;
		
		if (StringUtils.isNotBlank(response)) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			restCalendarResponse = objectMapper.readValue(response, DominoRestCalendarResponse.class);
		} else {
			restCalendarResponse = new DominoRestCalendarResponse();
		}
				
		return restCalendarResponse;
	}
	
}
