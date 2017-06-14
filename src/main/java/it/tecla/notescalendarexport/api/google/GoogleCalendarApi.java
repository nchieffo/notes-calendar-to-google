package it.tecla.notescalendarexport.api.google;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class GoogleCalendarApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCalendarApi.class);

	private static final String APP_NAME = "notes-calendar-export";
	private static final HttpTransport HTTP_TRANSPORT;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/" + APP_NAME);
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (GeneralSecurityException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = getClass().getResourceAsStream("/secret/google_client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	public Calendar getCalendarService() throws IOException {

		Credential credential = authorize();
		Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APP_NAME).build();

		LOGGER.debug("calendar service creted: {}", calendar);
		
		return calendar;
	}
	
	public String findClaendarId(String calendarName) throws Exception {
		
		Calendar calendar = this.getCalendarService();
		
		CalendarList calendarList = calendar.calendarList().list().execute();
		for (CalendarListEntry entry : calendarList.getItems()) {
			if (entry.getSummary().equals(calendarName)) {
				return entry.getId();
			}
		}
		
		return null;
	}
	
	public List<Event> getEvents(String calendarName) throws Exception {
		
		String calendarId = this.findClaendarId(calendarName);
		Calendar calendar = this.getCalendarService();
		Events events = calendar.events().list(calendarId).execute();
		
		return events.getItems();
	}
	
	public void updateCalendar(String calendarName, List<Event> newEvents) throws Exception {
		
		// devo cercare se ci sono eventi da aggiornare/cancellare
		
		List<Event> oldEvents = this.getEvents(calendarName);
		
		Map<Event, Event> eventsToBeUpdated = new HashMap<Event, Event>();
		List<Event> eventsToBeDeleted = new ArrayList<Event>();
		List<Event> eventsToBeCreated = new ArrayList<Event>();
		
		for (Event oldEvent: oldEvents) {
			for (Event newEvent : newEvents) {
				if (isSameEvent(oldEvent, newEvent)) {
					eventsToBeUpdated.put(oldEvent, newEvent);
				}
			}
			if (!eventsToBeUpdated.containsKey(oldEvent)) {
				eventsToBeDeleted.add(oldEvent);
			}
		}
		
		for (Event newEvent : newEvents) {
			if (eventsToBeUpdated.containsValue(newEvent)) {
				eventsToBeCreated.add(newEvent);
			}
		}
		
		
	}
	
	public boolean isSameEvent(Event ev1, Event ev2) {
		return ev1.getICalUID().equals(ev2.getICalUID());
	}

}
