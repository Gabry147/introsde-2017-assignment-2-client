package it.gabry147.client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.gabry147.entities.Activities;
import it.gabry147.entities.Activity;
import it.gabry147.entities.ActivityType;
import it.gabry147.entities.ActivityTypes;
import it.gabry147.entities.People;
import it.gabry147.entities.Person;

public class ClientMain {
	
	private Sender s;
	
	private void executeRequest(String type) throws JsonProcessingException, JAXBException, FileNotFoundException {
		// REQUEST 1
		People people = s.request_01(type);
		System.out.println(Print.printPeople(people, type));
		// save person ids
		s.first_person_id = people.getPersonList().get(0).getId();
		s.last_person_id = people.getPersonList().get(people.getPersonList().size()-1).getId();
		
		// REQUEST 2
		Person firstPerson = s.request_02(type);
		System.out.println(Print.printPerson(firstPerson, type));
		
		// REQUEST 3
		firstPerson.setFirstname("NewName"+type);
		Person newFirstPerson = s.request_03(firstPerson, type);
		System.out.println(Print.printPerson(newFirstPerson, type));
		
		// REQUEST 4
		//create person and activity
		Person toCreate = new Person();
		toCreate.setFirstname("ClientName");
		toCreate.setLastname("ClientLastname");
		toCreate.setBirthdate(new Date());
		List<Activity> activityList = new ArrayList<Activity>();
		Activity a = new Activity();
		a.setName("ClientActivity");
		a.setDescription("ClientDescription");
		a.setStartdate(new Date());
		ActivityType at = new ActivityType();
		at.setType("Social");
		a.setType(at);
		toCreate.setActivitypreference(activityList);
		//perform request
        Person createdPerson = s.request_04(toCreate, type);
        System.out.println(Print.printPerson(createdPerson, type));
        s.new_person = createdPerson.getId();
        
        // REQUEST 5
        s.request_05(type);
        
        // REQUEST 6
        ActivityTypes activityTypes = s.request_06(type);
        System.out.println(Print.printActivityTypes(activityTypes, type));
        s.types = activityTypes.getTypeList();
        
        // REQUEST 7
        Activities activities = null;
        for(int i=0; i<s.types.size() && activities == null; i++) {
        	String path = "/person/"+s.first_person_id+"/"+s.types.get(i).getType();
        	activities = s.single_request_07(type, path);
        }
        System.out.println(Print.printActivities(activities, type));
        s.activity = activities.getActivityList().get(0);
        
        // REQUEST 8
        Print.printActivity(s.request_08(type), type);
        
        // REQUEST 9
        Activity createdActivity = s.request_09(type);
        s.newActivity = createdActivity;
        System.out.println(Print.printActivity(createdActivity, type));
        
        // REQUEST 10
        createdActivity = s.request_10(type);
        System.out.println(Print.printActivity(createdActivity, type));
        
        // REQUEST 11
        Activities filtered = s.request_11(type);
        System.out.println(Print.printActivities(filtered, type));
	}
	
	public ClientMain() {
		s = new Sender();
	}
	
	public static void main(String args[]) throws JsonProcessingException, JAXBException, FileNotFoundException {
		ClientMain cm = new ClientMain();	
		//wake up app, if second call is failed there is a problem, contact the coder
		System.out.println("Client started. Waiting app...");
		
		if(! cm.s.wakeUpApp()) {
			System.out.println("First WakeUp failed");
			if(! cm.s.wakeUpApp()) {
				System.out.println("WakeUp failed, contact the coder");
				return;
			}
		}
		cm.s.out = new PrintWriter("client-json-log.txt");
		cm.executeRequest(MediaType.APPLICATION_JSON);
		System.out.println("\n\n");
		//reset data
		cm.s.first_person_id = 0;
		cm.s.last_person_id = 0;
		cm.s.new_person = 0;
		cm.s.activity = null;
		cm.s.newActivity = null;
		cm.s.types = null;
		cm.s.out.close();
		cm.s.out = new PrintWriter("client-xml-log.txt");
		cm.executeRequest(MediaType.APPLICATION_XML);
		cm.s.out.close();
		
	}

}
