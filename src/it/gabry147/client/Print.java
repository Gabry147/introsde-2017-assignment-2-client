package it.gabry147.client;

import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import it.gabry147.entities.Activities;
import it.gabry147.entities.Activity;
import it.gabry147.entities.ActivityTypes;
import it.gabry147.entities.People;
import it.gabry147.entities.Person;

public class Print {
	
	private static String printJSON(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        String result = mapper.writeValueAsString(obj);
        return result;	
	}
	
	private static String printXML(JAXBContext con, Object obj) throws JAXBException {	
		Marshaller marshaller = con.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", true);
		StringWriter sw = new StringWriter();
		marshaller.marshal(obj, sw);
		return sw.toString();	
	}
	
	public static String printPeople(People p, String type) throws JAXBException, JsonProcessingException {
		if(type.equals(MediaType.APPLICATION_JSON)) {
			return printJSON(p);			
		}
		else if(type.equals(MediaType.APPLICATION_XML)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(People.class);
			return printXML(jaxbContext, p);		
		}
		return null;
	}
	
	public static String printPerson(Person p, String type) throws JAXBException, JsonProcessingException {
		if(type.equals(MediaType.APPLICATION_JSON)) {
			return printJSON(p);			
		}
		else if(type.equals(MediaType.APPLICATION_XML)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
			return printXML(jaxbContext, p);		
		}
		return null;
	}
	
	public static String printActivityTypes(ActivityTypes at, String type) throws JAXBException, JsonProcessingException {
		if(type.equals(MediaType.APPLICATION_JSON)) {
			return printJSON(at);			
		}
		else if(type.equals(MediaType.APPLICATION_XML)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(ActivityTypes.class);
			return printXML(jaxbContext, at);		
		}
		return null;
	}
	
	public static String printActivity(Activity a, String type) throws JAXBException, JsonProcessingException {
		if(type.equals(MediaType.APPLICATION_JSON)) {
			return printJSON(a);			
		}
		else if(type.equals(MediaType.APPLICATION_XML)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Activity.class);
			return printXML(jaxbContext, a);		
		}
		return null;
	}
	
	public static String printActivities(Activities as, String type) throws JAXBException, JsonProcessingException {
		if(type.equals(MediaType.APPLICATION_JSON)) {
			return printJSON(as);			
		}
		else if(type.equals(MediaType.APPLICATION_XML)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Activities.class);
			return printXML(jaxbContext, as);		
		}
		return null;
	}

	public static String printResponseStatus(String requestResult, int status) {
		String result = "\n";
		result += "=> RESULT: " + requestResult +"\n";
		result += "=> HTTP Status: " + status +"\n";
		return result;
	}
}
