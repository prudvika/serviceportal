package zhulin.project.serviceportal;

import java.util.List;
import java.util.Map;


public class DashboardObjectMapping {
	private String deviceType;
	private String dashboardType;
	private Map<String,List<DashboardAttributeMapping>> mappings;
	
	public DashboardObjectMapping(String deviceType,String dashboardType,Map<String,List<DashboardAttributeMapping>> mappings){
		this.deviceType=deviceType;
		this.dashboardType=dashboardType;
		this.mappings=mappings;
	}
	
	public String getDeviceType(){
		return this.deviceType;
	}
	
	public String getDashboardType(){
		return this.dashboardType;
	}
	
	public Map<String,List<DashboardAttributeMapping>> getMappings(){
		return this.mappings;
	}
}
