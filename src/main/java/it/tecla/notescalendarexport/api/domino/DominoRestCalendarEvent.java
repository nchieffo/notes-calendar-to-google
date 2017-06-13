package it.tecla.notescalendarexport.api.domino;

import java.util.Date;

/**
 * https://www-10.lotus.com/ldd/ddwiki.nsf/xpAPIViewer.xsp?lookupName=IBM+Domino+Access+Services+9.0.1#action=openDocument&res_title=JSON_representation_of_an_event_das901&content=apicontent&sa=true
 * @author tecla
 *
 */
public class DominoRestCalendarEvent {

	private String href;
	private String id;
	private String summary;
	private Date start;
	private Date end;
	private String clazz;
	private String transparency;
	private int sequence;
	
	// "x-lotus-organizer" : { "data":"paolo.zago@methode.it" }
	
	// "x-lotus-noticetype": { "data":"A" }
	
	// "x-lotus-appttype": { "data":"3" }
	
	// 
	
	/**
	 * Defines the scope of the access that the calendar owner intends for information within an individual calendar entry
	 * @return public/private
	 */
	public String getClazz() {
		return clazz;
	}
	
	/**
	 * Defines whether an event is transparent or not to busy time searches.
	 * @return opaque/transparent
	 */
	public String getTransparency() {
		return transparency;
	}
}
