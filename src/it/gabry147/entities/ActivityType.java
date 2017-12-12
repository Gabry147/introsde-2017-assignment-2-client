package it.gabry147.entities;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.annotation.JsonValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ActivityType implements Serializable{

	private static final long serialVersionUID = 1L;
	@XmlValue
    protected String type;

	@JsonValue
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ActivityType() {
		//needed for XML
	}
	
	//need this function to read json entity
	public ActivityType(String s) {
		this.setType(s);
	}
}