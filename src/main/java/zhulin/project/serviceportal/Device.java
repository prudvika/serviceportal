package zhulin.project.serviceportal;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


public class Device {
	private int id;
	private String name;
	private String type;
	private Map<String,String> attributes;
	
	public Device(){	
	}
	
	public Device(int id,String name,String type,Map<String,String> customizedAttributeValues){
		this.id=id;
		this.name=name;
		this.type=type;
		this.attributes=customizedAttributeValues;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setAttributes(Map<String,String> attributes){
		this.attributes=attributes;
	}
	
	public Map<String,String> getAttributes(){
		return this.attributes;
	}
	
	public JsonObject toJSON(){
		JsonObjectBuilder objectBuilder=Json.createObjectBuilder()
				.add("type", getType()!=null?getType():" ")
				.add("name", getName()!=null?getName():" ");
		for(String attribute:getAttributes().keySet()){
			objectBuilder.add(attribute, getAttributes().get(attribute));
		}
		
		return objectBuilder.build();
	}
}
