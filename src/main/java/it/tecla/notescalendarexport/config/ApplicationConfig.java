package it.tecla.notescalendarexport.config;

public class ApplicationConfig {
	
	public String getDominoRestCalendarUrl() {
		return "https://webmail.tecla.it/mail/nchieffo.nsf/api/calendar/events";
	}

	public String getDominoRestCalendarUsername() {
		return "n.chieffo";
	}

	public String getDominoRestCalendarPassword() {
		return "teclait#chieffo";
	}
	
	public String getINotesBaseUrl() {
		return "https://webmail.tecla.it/mail/nchieffo.nsf/$Calendar";
	}
	
	public String getINotesUsername() {
		return "n.chieffo";
	}
	
	public String getINotesPassword() {
		return "teclait#chieffo";
	}

	public int getConnectTimeout() {
		return 10000;
	}

	public int getReadTimeout() {
		return 10000;
	}
	
	public static ApplicationConfig build() {
		return new ApplicationConfig();
	}
	
}
