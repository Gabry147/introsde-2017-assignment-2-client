package it.gabry147.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "id")
    protected int id;

    protected String firstname;

    protected String lastname;
   
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    protected Date birthdate;
   
    @XmlElementWrapper(name = "activitypreference")
    @XmlElement(name = "activity")
    protected List<Activity> activitypreference;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<Activity> getActivitypreference() {
		return activitypreference;
	}

	public void setActivitypreference(List<Activity> activitypreference) {
		this.activitypreference = activitypreference;
	}
	
	public Person() {
		//needed for XML
	}
}
