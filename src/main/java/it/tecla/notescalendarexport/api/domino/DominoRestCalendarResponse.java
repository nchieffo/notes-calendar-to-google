package it.tecla.notescalendarexport.api.domino;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DominoRestCalendarResponse {

	private List<DominoRestCalendarEvent> events = new ArrayList<DominoRestCalendarEvent>();
	
	public List<DominoRestCalendarEvent> getEvents() {
		return events;
	}
	
	public void setEvents(List<DominoRestCalendarEvent> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
