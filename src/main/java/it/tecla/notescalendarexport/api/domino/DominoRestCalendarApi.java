package it.tecla.notescalendarexport.api.domino;

import java.net.URI;
import java.util.logging.Level;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.tecla.notescalendarexport.config.ApplicationConfig;

public class DominoRestCalendarApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DominoRestCalendarApi.class);
	private static final java.util.logging.Logger JUL_LOGGER = java.util.logging.Logger.getLogger(DominoRestCalendarApi.class.getName());
	
	private ApplicationConfig config = ApplicationConfig.build();

	public String getCalendarEntries() {
		
		String calendarUrl = config.getDominoRestCalendarUrl();
		
		Feature loggingFeature = new LoggingFeature(JUL_LOGGER, Level.FINEST, null, null);
		Feature authenticationFeature = HttpAuthenticationFeature.basic(config.getDominoRestCalendarUsername(), config.getDominoRestCalendarPassword());

		Client restClient = ClientBuilder.newBuilder()
				.register(loggingFeature)
				.register(authenticationFeature)
				.build();
		
		URI uri = UriBuilder.fromUri(calendarUrl).build();
		LOGGER.debug("uri: {}", uri);
		
		Response clientResponse = restClient.target(uri).request().get();
		
		Family respnseFamily = clientResponse.getStatusInfo().getFamily();
		LOGGER.debug("response status: {} {}", clientResponse.getStatus(), respnseFamily);
		LOGGER.debug("response type: {}", clientResponse.getMediaType());
		
		String ical = clientResponse.readEntity(String.class);
				
		return ical;
	}
	
	public static void main(String[] args) {
		// TODO cancellare metodo main
		String ical = new DominoRestCalendarApi().getCalendarEntries();
		System.out.println(ical);
	}
	
}
