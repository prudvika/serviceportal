package zhulin.project.serviceportal;

import java.util.Set;

public class DashboardObject {
	private String name;
	private Set<DashboardAttribute> properties;
	
	public DashboardObject(String name,Set<DashboardAttribute> properties){
		this.name=name;
		this.properties=properties;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Set<DashboardAttribute> getProperties(){
		return this.properties;
	}
}
