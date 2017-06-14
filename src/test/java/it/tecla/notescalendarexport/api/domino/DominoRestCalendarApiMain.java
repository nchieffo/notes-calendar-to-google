package it.tecla.notescalendarexport.api.domino;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DominoRestCalendarApiMain {
	
	public static void main(String[] args) throws Exception {
		
		Date start = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.parse("2017-01-01T00:00:00");
		Date end = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.parse("2017-12-31T23:59:59");
		
		List<DominoRestCalendarEvent> response = new DominoRestCalendarApi().getCalendarEntries(start, end);
		System.out.println(response);
		System.out.println(response.size());
	}
	
}
