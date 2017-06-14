package it.tecla.notescalendarexport.api.inotes;

import java.net.URI;
import java.util.Date;
import java.util.logging.Level;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.tecla.notescalendarexport.config.ApplicationConfig;
import it.tecla.notescalendarexport.notes.NotesCalendarException;

public class INotesCalendarReadViewEntriesApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(INotesCalendarReadViewEntriesApi.class);
	private static final java.util.logging.Logger JUL_LOGGER = java.util.logging.Logger.getLogger(INotesCalendarReadViewEntriesApi.class.getName());
	
	private ApplicationConfig config = ApplicationConfig.build();

	public INotesCalendarReadViewEntriesResponse getCalendarEntries(Date startDate, Date endDate) throws NotesCalendarException {
		
		// https://webmail.tecla.it/mail/nchieffo.nsf/$Calendar?ReadViewEntries&KeyType=time&StartKey=20170101T000000&UntilKey=20171231T235959
		
		String baseUrl = config.getINotesBaseUrl();
		LOGGER.debug("baseUrl: {}", baseUrl);
		
		URI uri = UriBuilder.fromPath(baseUrl)
			.queryParam("ReadViewEntries", "")
			.queryParam("KeyType", "time")
			.queryParam("Count", "1000")
			.queryParam("StartKey", DateFormatUtils.format(startDate, "yyyyMMdd'T'000000"))
			.queryParam("UntilKey", DateFormatUtils.format(endDate, "yyyyMMdd'T'235959"))
			.build();

		LOGGER.debug("uri: {}", uri);

		Feature loggingFeature = new LoggingFeature(JUL_LOGGER, Level.FINEST, null, null);
		Feature authenticationFeature = HttpAuthenticationFeature.basic(config.getINotesUsername(), config.getINotesPassword());

		Client restClient = ClientBuilder.newBuilder()
				.register(loggingFeature)
				.register(authenticationFeature)
				.build();
		
		Response clientResponse = restClient.target(uri).request().get();
		
		Family respnseFamily = clientResponse.getStatusInfo().getFamily();
		LOGGER.debug("response status: {} {}", clientResponse.getStatus(), respnseFamily);
		LOGGER.debug("response type: {}", clientResponse.getMediaType());
		
		if (!respnseFamily.equals(Response.Status.Family.SUCCESSFUL)) {
			String msg = (String) clientResponse.readEntity(String.class);
			throw new NotesCalendarException("error " + clientResponse.getStatus() + " " +  respnseFamily + " while reading inotes calendar feed " + uri + ": " + msg);
		}
		
		if (!clientResponse.getMediaType().isCompatible(MediaType.TEXT_XML_TYPE)) {
			throw new NotesCalendarException("wrong credentials while reading inotes calendar feed " + uri);
		}
		
		Viewentries viewentries = clientResponse.readEntity(Viewentries.class);
		LOGGER.trace("viewentries: {}", viewentries);
		
		INotesCalendarReadViewEntriesResponse iNotesCalendarReaderResponse = new INotesCalendarReadViewEntriesResponse();
		iNotesCalendarReaderResponse.setViewentries(viewentries);
		return iNotesCalendarReaderResponse;
	}

}
