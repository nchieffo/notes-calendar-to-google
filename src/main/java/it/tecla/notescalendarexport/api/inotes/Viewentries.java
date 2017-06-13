package it.tecla.notescalendarexport.api.inotes;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement(name="viewentries")
@XmlAccessorType(XmlAccessType.FIELD)
public class Viewentries {
	
	@XmlAttribute
	private String timestamp;

	private List<Viewentry> viewentry;

	@XmlAttribute
	private String startindex;

	@XmlAttribute
	private String toplevelentries;

	@XmlAttribute
	private String rangeentries;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<Viewentry> getViewentry() {
		return viewentry;
	}

	public void setViewentry(List<Viewentry> viewentry) {
		this.viewentry = viewentry;
	}

	public String getStartindex() {
		return startindex;
	}

	public void setStartindex(String startindex) {
		this.startindex = startindex;
	}

	public String getToplevelentries() {
		return toplevelentries;
	}

	public void setToplevelentries(String toplevelentries) {
		this.toplevelentries = toplevelentries;
	}

	public String getRangeentries() {
		return rangeentries;
	}

	public void setRangeentries(String rangeentries) {
		this.rangeentries = rangeentries;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
