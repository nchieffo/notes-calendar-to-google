package it.tecla.notescalendarexport.api.inotes;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
public class Datetimelist {

	private List<String> datetime;
	
	public List<String> getDatetime() {
		return datetime;
	}
	
	public void setDatetime(List<String> text) {
		this.datetime = text;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
