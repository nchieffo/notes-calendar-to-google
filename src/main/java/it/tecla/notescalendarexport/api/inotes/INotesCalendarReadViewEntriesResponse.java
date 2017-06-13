package it.tecla.notescalendarexport.api.inotes;

public class INotesCalendarReadViewEntriesResponse {
	
	private Viewentries viewentries;

	public Viewentries getViewentries() {
		return viewentries;
	}

	public void setViewentries(Viewentries viewentries) {
		this.viewentries = viewentries;
	}

	@Override
	public String toString() {
		return "ClassPojo [viewentries = " + viewentries + "]";
	}
}
