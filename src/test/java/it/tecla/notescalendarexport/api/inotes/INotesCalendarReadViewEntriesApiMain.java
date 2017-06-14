package it.tecla.notescalendarexport.api.inotes;

import org.apache.commons.lang3.time.DateUtils;

public class INotesCalendarReadViewEntriesApiMain {
	
	public static void main(String[] args) throws Exception {
		System.out.println(new INotesCalendarReadViewEntriesApi().getCalendarEntries(DateUtils.parseDate("2017-01-01", "yyyy-MM-dd"), DateUtils.parseDate("2017-12-31", "yyyy-MM-dd")));
	}
	
}
