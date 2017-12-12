package it.gabry147.entities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Activity implements Serializable{

	private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "id")
    protected int id;

    protected String name;
    
    protected String description;

    protected String place;

    @XmlElement(name = "type")
    protected ActivityType type;
       
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    protected Date startdate;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public Activity() {
		//needed for XML
	}
}
