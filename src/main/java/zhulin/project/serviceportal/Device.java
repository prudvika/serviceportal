package zhulin.project.serviceportal;

import java.util.Map;

public class Device {
	private int id;
	private String name;
	private Map<String,String> attributes;
	
	public Device(int id,String name,Map<String,String> customizedAttributeValues){
		this.id=id;
		this.name=name;
		this.attributes=customizedAttributeValues;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Map<String,String> getAttributes(){
		return this.attributes;
	}
}
