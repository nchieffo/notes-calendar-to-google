package it.tecla.notescalendarexport.notes.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DateUtils;

import it.tecla.notescalendarexport.api.inotes.Entrydata;
import it.tecla.notescalendarexport.api.inotes.INotesCalendarReadViewEntriesApi;
import it.tecla.notescalendarexport.api.inotes.INotesCalendarReadViewEntriesResponse;
import it.tecla.notescalendarexport.api.inotes.Viewentries;
import it.tecla.notescalendarexport.api.inotes.Viewentry;
import it.tecla.notescalendarexport.entity.CalendarEntry;
import it.tecla.notescalendarexport.entity.CalendarEntry.EventType;
import it.tecla.notescalendarexport.notes.NotesCalendarDao;
import it.tecla.notescalendarexport.notes.NotesCalendarException;

public class INotesCalendarReadViewEntriesDao implements NotesCalendarDao {

	private INotesCalendarReadViewEntriesApi calendarApi = new INotesCalendarReadViewEntriesApi();

	public Collection<CalendarEntry> find(Date startDate, Date endDate) throws NotesCalendarException {

		INotesCalendarReadViewEntriesResponse response = calendarApi.getCalendarEntries(startDate, endDate);
		Viewentries viewentries = response.getViewentries();

//		List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>(viewentries.getViewentry().size());
		Map<String, CalendarEntry> calendarEntries = new TreeMap<String, CalendarEntry>();

		for (Viewentry viewentry : viewentries.getViewentry()) {
			
			String unid = viewentry.getUnid();

			CalendarEntry calendarEntry = calendarEntries.get(unid);
			
			if (calendarEntry == null) {
				calendarEntry = new CalendarEntry();
				calendarEntry.setId(unid);
			} else {
				// TODO qui bisogna sovrascrivere la data di fine
			}
			
			{
				// setup event type
				Entrydata entrydata = viewentry.getEntrydata().get(1);
				
				EventType eventType = null;

				Integer type = entrydata.getNumber();
				if (type == null && entrydata.getNumberlist() != null
						&& entrydata.getNumberlist().getNumber().size() > 0) {
					
					type = entrydata.getNumberlist().getNumber().get(0);
				}

				if (type != null) {

					/*
					 * 194 all day event 195 reminder 205 appuntamento 187
					 * meeting non processato ??? meeting non accettato 210
					 * meeting accettato
					 * 
					 * 204 importante
					 */

					if (type.equals("194")) {
						eventType = EventType.ALL_DAY_EVENT;
					} else if (type.equals("195")) {
						eventType = EventType.REMINDER;
					} else if (type.equals("205")) {
						eventType = EventType.APPOINTMENT;
					} else if (type.equals("187") || type.equals("210")) {
						eventType = EventType.MEETING;
					}

					calendarEntry.setEventType(eventType);

				}
			}

			for (Entrydata entrydata : viewentry.getEntrydata()) {
				
				if (entrydata.getColumnnumber().equals("0")) {
					Date date = getDate(entrydata, calendarEntry);
					calendarEntry.setStartDate(date);
				} else if (entrydata.getColumnnumber().equals("1")) {
					// già fatto in precedenza
				} else if (entrydata.getColumnnumber().equals("4")) {
					Date date = getDate(entrydata, calendarEntry);

					calendarEntry.setEndDate(date);
				} else if (entrydata.getColumnnumber().equals("5")) {

					String title = entrydata.getText();
					String location = null;
					String chairman = null;

					if (title == null && entrydata.getTextlist() != null
							&& entrydata.getTextlist().getText().size() > 0) {
						List<String> textlist = entrydata.getTextlist().getText();
						title = textlist.get(0);

						if (textlist.size() == 3) {
							chairman = textlist.get(1);
							location = textlist.get(2);
						} else {

							if (calendarEntry.getEventType() == EventType.APPOINTMENT) {
								if (textlist.size() == 2) {
									location = textlist.get(1);
								}
							} else if (calendarEntry.getEventType() == EventType.MEETING) {
								if (textlist.size() == 2) {
									chairman = textlist.get(1);
								}
							}
						}
					}

					calendarEntry.setTitle(title);
					calendarEntry.setLocation(location);
					calendarEntry.setChairman(chairman);
				}
			}

			calendarEntries.put(calendarEntry.getId(), calendarEntry);

		}

		return calendarEntries.values();
	}

	private Date getDate(Entrydata entrydata, CalendarEntry calendarEntry) throws NotesCalendarException {

		Date date = null;

		String datetime = entrydata.getDatetime();
		if (datetime == null && entrydata.getDatetimelist() != null) {
			datetime = entrydata.getDatetimelist().getDatetime().get(0);
		}

		if (datetime != null) {

			// 20170117T090000,00+01
			try {
				date = DateUtils.parseDate(datetime.split(",")[0], "yyyyMMdd'T'HHmmss");
				
				if (calendarEntry.getEventType() == EventType.ALL_DAY_EVENT) {
					date = DateUtils.truncate(date, Calendar.DATE);
				}
				
			} catch (ParseException ex) {
				throw new NotesCalendarException(ex);
			}
		}
		return date;
	}

	public static void main(String[] args) throws Exception {
		// TODO cancellare metodo main
		INotesCalendarReadViewEntriesDao dao = new INotesCalendarReadViewEntriesDao();
		System.out.println(dao.find(DateUtils.parseDate("2017-01-01", "yyyy-MM-dd"),
				DateUtils.parseDate("2017-12-31", "yyyy-MM-dd")));

	}

}
