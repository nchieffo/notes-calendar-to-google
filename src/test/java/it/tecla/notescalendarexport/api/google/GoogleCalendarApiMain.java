package it.tecla.notescalendarexport.api.google;

import java.util.List;

import com.google.api.services.calendar.model.Event;

public class GoogleCalendarApiMain {
	
	public static void main(String[] args) throws Exception {

		GoogleCalendarApi googleCalendarApi = new GoogleCalendarApi();
		
		List<Event> events = googleCalendarApi.getEvents("TECLA");
		
		for (Event event : events) {
			System.out.println(event.getICalUID());
		}
	}

}
