package it.tecla.notescalendarexport.api.inotes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entrydata {

	@XmlAttribute
	private String name;

	private String datetime;
	
	private Datetimelist datetimelist;

	private String text;
	
	private Textlist textlist;
	
	private Integer number;
	
	private Numberlist numberlist;

	@XmlAttribute
	private String columnnumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	public Datetimelist getDatetimelist() {
		return datetimelist;
	}
	
	public void setDatetimelist(Datetimelist datetimelist) {
		this.datetimelist = datetimelist;
	}

	public String getColumnnumber() {
		return columnnumber;
	}

	public void setColumnnumber(String columnnumber) {
		this.columnnumber = columnnumber;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Textlist getTextlist() {
		return textlist;
	}
	
	public void setTextlist(Textlist textlist) {
		this.textlist = textlist;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Numberlist getNumberlist() {
		return numberlist;
	}
	
	public void setNumberlist(Numberlist numberlist) {
		this.numberlist = numberlist;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
