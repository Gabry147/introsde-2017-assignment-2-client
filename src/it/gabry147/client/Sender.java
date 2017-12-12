package it.gabry147.client;

import java.io.PrintWriter;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import it.gabry147.entities.Activities;
import it.gabry147.entities.Activity;
import it.gabry147.entities.ActivityType;
import it.gabry147.entities.ActivityTypes;
import it.gabry147.entities.People;
import it.gabry147.entities.Person;

public class Sender {
	
	private String baseURI = "https://sde2-2017-scarton.herokuapp.com/";
	
	private WebTarget webtarget;
	
	PrintWriter out;
	
	//Data for requests
	int first_person_id;
	int last_person_id;
	int new_person;
	List<ActivityType> types;
	Activity activity;
	Activity newActivity;
	
	//create the webtarget based on baseURI
	public Sender() {
    	ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        URI uri = UriBuilder
        		.fromUri(baseURI).build();
        webtarget = client.target(uri);	
    }
	
	//make a GET request to baseURI, if the server return the String "Started" should be ready
	public boolean wakeUpApp() {
		Response response = this.createGETrequest("", MediaType.TEXT_PLAIN);
		String body = response.readEntity(String.class);
		System.out.println(body);
		return body.equals("Started");
	}
	
	
	/*
	 * #############################################
	 * Auxiliary function to make requests
	 * #############################################
	 */
	private Response createGETrequest(String path, String type) {
    	return webtarget
    			.path(path)
    			.request()
    			.accept(type)
    			.get();
    }
    
    private Response createPOSTrequest(String path, String type, @SuppressWarnings("rawtypes") Entity entity) {
    	return webtarget
    			.path(path)
    			.request(type)
    			.accept(type)
    			.post(entity);
    }
    
    private Response createPUTrequest(String path, String type, @SuppressWarnings("rawtypes") Entity entity) {
    	return webtarget
    			.path(path)
    			.request()
    			.accept(type)
    			.put(entity);
    }
    
    private Response createDELETErequest(String path, String type) {
    	return webtarget
    			.path(path)
    			.request()
    			.accept(type)
    			.delete();
    }  
    
	/*
	 * #############################################
	 * One functions for each request
	 * #############################################
	 */
    public People request_01(String type) {
    	Response response = this.createGETrequest("person", type);
    	People p = (People) response.readEntity(People.class);
    	List<Person> pl = p.getPersonList();
    	String result = null;
    	if( pl.size() >= 5) {
    		result = "OK";
    		first_person_id = pl.get(0).getId();
    		last_person_id = pl.get(pl.size()-1).getId();
    	}
    	else {
    		result = "ERROR";
    		return null;
    	}
    	out.println("Request #1: GET /person Accept: "+type);
    	System.out.println("Request #1: GET /person Accept: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	out.println(toWrite);
    	System.out.println(toWrite);
    	return p;
    }
    
    public Person request_02(String type) {
    	Response response = this.createGETrequest("person/"+this.first_person_id, type);
    	Person p = (Person) response.readEntity(Person.class);
    	String result = null;
    	if( p != null ) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #2: GET /person/"+ this.first_person_id + " Accept: "+type);
    	System.out.println("Request #2: GET /person/"+ this.first_person_id + " Accept: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return p;
    }

    public Person request_03(Person toModify, String type) {
    	Entity<Person> e = Entity.entity(toModify, type);
    	Response response = this.createPUTrequest("person/"+this.first_person_id, type, e);
    	Person p = (Person) response.readEntity(Person.class);
    	String result = null;
    	if( p.getFirstname().equals(toModify.getFirstname() )) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #3: PUT /person/"+ this.first_person_id + " Accept: "+type+ " Content-Type: "+type);
    	System.out.println("Request #3: PUT /person/"+ this.first_person_id + " Accept: "+type+ " Content-Type: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return p;
    }
    
    public Person request_04(Person toInsert, String type) {
    	Entity<Person> e = Entity.entity(toInsert, type);
    	Response response = this.createPOSTrequest("person/", type, e);
    	Person p = (Person) response.readEntity(Person.class);
    	String result = null;
    	if( p != null) {
    		new_person = p.getId();
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #4: POST /person Accept: "+type+ " Content-Type: "+type);
    	System.out.println("Request #4: POST /person Accept: "+type+ " Content-Type: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return p;
    }
    
    public void request_05(String type) {
    	this.createDELETErequest("person/"+this.new_person, type);
    	Response response = this.createGETrequest("person/"+this.new_person, type);
    	String result = null;
    	if( response.getStatus() == Response.Status.NOT_FOUND.getStatusCode() ) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #5: DELETE /person/"+this.new_person +" Accept: "+type+ " Content-Type: "+type);
    	System.out.println("Request #5: DELETE /person/"+this.new_person +" Accept: "+type+ " Content-Type: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    }
    
    public ActivityTypes request_06(String type) {
    	Response response = this.createGETrequest("activity_types", type);
    	ActivityTypes at = (ActivityTypes) response.readEntity(ActivityTypes.class);
    	List<ActivityType> atl = at.getTypeList();
    	String result = null;
    	if( atl.size() >= 2) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #6: GET /activity_types Accept: "+type);
    	System.out.println("Request #6: GET /activity_types Accept: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return at;
    }
    
    //request #7 ask to make multiple requests until a valid resource is found, this function make a single request and it will be called multiple times
    public Activities single_request_07(String type, String path) {
    	Response response = this.createGETrequest(path, type);
    	Activities as = (Activities) response.readEntity(Activities.class);
    	List<Activity> asl = as.getActivityList();
    	String result = null;
    	if(asl == null) {
    		result = "ERROR";
    		return null;
    	}
    	if( asl.size() >= 1) {
    		result = "OK";
    		out.println("Request #7: GET "+path+" Accept: "+type);
    		System.out.println("Request #7: GET "+path+" Accept: "+type);
        	String toWrite = Print.printResponseStatus(result, response.getStatus());
        	System.out.println(toWrite);
        	out.println(toWrite);
        	return as;
    	}
    	else {
    		result = "ERROR";
    		return null;
    	}
    }
    
    public Activity request_08(String type) {
    	Response response = this.createGETrequest(
    			"person/"+this.first_person_id+"/"+activity.getType().getType()+"/"+activity.getId(), 
    			type);
    	Activity a = (Activity) response.readEntity(Activity.class);
    	String result = null;
    	if( a != null ) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #8: GET /person/"+this.first_person_id+"/"+activity.getType().getType()+"/"+activity.getId() + " Accept: "+type);
    	System.out.println("Request #8: GET /person/"+this.first_person_id+"/"+activity.getType().getType()+"/"+activity.getId() + " Accept: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return a;
    }
    
    public Activity request_09(String type) {
    	Response response1 = this.createGETrequest(
    			"person/"+this.first_person_id+"/"+this.types.get(0).getType(),
    			type);
    	Activities as = (Activities) response1.readEntity(Activities.class);
    	int beforeSize = 0;
    	if(as.getActivityList() != null) beforeSize = as.getActivityList().size();
    	
    	Activity a = new Activity();
    	a.setName("Client_"+type);
    	a.setDescription("Description "+type);
    	a.setPlace("Client");
    	a.setType(this.types.get(0));
    	a.setStartdate(new Date());
    	Entity<Activity> e = Entity.entity(a, type);
    	Response response = this.createPOSTrequest("person/"+this.first_person_id+"/"+this.types.get(0).getType(), type, e);
    	Activity persisted = (Activity) response.readEntity(Activity.class);
    	
    	Response response2 = this.createGETrequest(
    			"person/"+this.first_person_id+"/"+this.types.get(0).getType(),
    			type);
    	Activities as2 = (Activities) response2.readEntity(Activities.class);
    	int afterSize = as2.getActivityList().size();
    	
    	String result = null;
    	if(beforeSize+1 == afterSize) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #9: POST /person/"+this.first_person_id+"/"+this.types.get(0).getType()+" Accept: "+type+ " Content-Type: "+type);
		System.out.println("Request #9: POST /person/"+this.first_person_id+"/"+this.types.get(0).getType()+" Accept: "+type+ " Content-Type: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return persisted;
    }
    
    public Activity request_10(String type) {
    	//entity not used by server but needed to avoid nullpointerexception
    	Response response = this.createPUTrequest(
    			"person/"+this.first_person_id+"/"+this.types.get(1).getType()+"/"+newActivity.getId(), 
    			type, Entity.entity(this.newActivity, type));
    	Activity a = (Activity) response.readEntity(Activity.class);
    	String result = null;
    	if( a.getType().getType().equals(types.get(1).getType()) ) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #10: PUT /person/"+this.first_person_id+"/"+this.types.get(1).getType()+"/"+newActivity.getId() + " Accept: "+type);
    	System.out.println("Request #10: PUT /person/"+this.first_person_id+"/"+this.types.get(1).getType()+"/"+newActivity.getId() + " Accept: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return a;
    }
    
    public Activities request_11(String type) {   	
    	Response response = webtarget
    			.path("person/"+this.first_person_id+"/"+this.types.get(1).getType())
    			.queryParam("before", "2018-11-24") //last activity created should be in that range
    			.queryParam("after", "2017-11-24")
    			.request()
    			.accept(type)
    			.get();
    	Activities as = (Activities) response.readEntity(Activities.class);
    	String result = null;
    	if( as.getActivityList() != null) {
    		result = "OK";
    	}
    	else {
    		result = "ERROR";
    	}
    	out.println("Request #11: GET /person/"+this.first_person_id+"/"+this.types.get(1).getType() +" Accept: "+type);
    	System.out.println("Request #11: GET /person/"+this.first_person_id+"/"+this.types.get(1).getType() +" Accept: "+type);
    	String toWrite = Print.printResponseStatus(result, response.getStatus());
    	System.out.println(toWrite);
    	out.println(toWrite);
    	return as;
    }
}
