package it.tecla.notescalendarexport.notes;

public class NotesCalendarException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NotesCalendarException(Exception ex) {
		super(ex);
	}
	
	public NotesCalendarException(String msg) {
		super(msg);
	}
	
}
