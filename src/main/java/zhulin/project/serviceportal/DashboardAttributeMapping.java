package zhulin.project.serviceportal;


public class DashboardAttributeMapping {
	private String dashboardAttribute;
	private String deviceAttribute;
	private String operator;
	private String deviceValue;
	private String constantValue;
	
	public DashboardAttributeMapping(String dashboardAttribute,String deviceAttribute
			,String operator,String deviceValue,String constantValue){
		this.dashboardAttribute=dashboardAttribute;
		this.deviceAttribute=deviceAttribute;
		this.operator=operator;
		this.deviceValue=deviceValue;
		this.constantValue=constantValue;
	}
	
	public DashboardAttributeMapping(String dashboardAttribute,String constantValue){
		this(dashboardAttribute,null,null,null,constantValue);
	}
	
	public String getDashboardAttribute(){
		return this.dashboardAttribute;
	}
	
	public void setDeviceAttribute(String deviceAttribute){
		this.deviceAttribute=deviceAttribute;
	}
	
	public String getDeviceAttribute(){
		return this.deviceAttribute;
	}
	
	public String getOperator(){
		return this.operator;
	}
	
	public String getDeviceValue(){
		return this.deviceValue;
	}
	
	public String getConstantValue(){
		return this.constantValue;
	}
}
