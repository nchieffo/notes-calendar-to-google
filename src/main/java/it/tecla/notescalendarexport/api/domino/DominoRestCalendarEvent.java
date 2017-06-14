package it.tecla.notescalendarexport.api.domino;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://www-10.lotus.com/ldd/ddwiki.nsf/xpAPIViewer.xsp?lookupName=IBM+Domino+Access+Services+9.0.1#action=openDocument&res_title=JSON_representation_of_an_event_das901&content=apicontent&sa=true
 * @author tecla
 *
 */
public class DominoRestCalendarEvent {

	private String href;
	private String id;
	private String summary;
	private DominoRestCalendarDate start;
	private DominoRestCalendarDate end;
	@JsonProperty("class")
	private String clazz;
	private String transparency;
	private int sequence;
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public DominoRestCalendarDate getStart() {
		return start;
	}
	
	public void setStart(DominoRestCalendarDate start) {
		this.start = start;
	}
	
	public DominoRestCalendarDate getEnd() {
		return end;
	}
	
	public void setEnd(DominoRestCalendarDate end) {
		this.end = end;
	}
	
	/**
	 * Defines the scope of the access that the calendar owner intends for information within an individual calendar entry
	 * @return public/private
	 */
	public String getClazz() {
		return clazz;
	}
	
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * Defines whether an event is transparent or not to busy time searches.
	 * @return opaque/transparent
	 */
	public String getTransparency() {
		return transparency;
	}
	
	public void setTransparency(String transparency) {
		this.transparency = transparency;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
