package it.tecla.notescalendarexport.notes;

import java.util.Collection;
import java.util.Date;

import it.tecla.notescalendarexport.entity.CalendarEntry;

public interface NotesCalendarDao {

	public Collection<CalendarEntry> find(Date startDate, Date endDate) throws NotesCalendarException;
	
}
