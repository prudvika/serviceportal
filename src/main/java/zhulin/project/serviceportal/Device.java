package zhulin.project.serviceportal;

import java.util.Map;

public class Device {
	private int id;
	private String name;
	private String type;
	private Map<String,String> attributes;
	
	public Device(int id,String name,String type,Map<String,String> customizedAttributeValues){
		this.id=id;
		this.name=name;
		this.type=type;
		this.attributes=customizedAttributeValues;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return type;
	}
	
	public Map<String,String> getAttributes(){
		return this.attributes;
	}
}
