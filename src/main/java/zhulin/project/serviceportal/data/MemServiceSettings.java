package zhulin.project.serviceportal.data;

public class MemServiceSettings implements ServiceSettings {
	private String dmServiceURL="http://192.168.31.232:8080/devicemanager-1.0/";
	private String msServiceURL="http://192.168.31.232:81/monitorservice-0.1/";
	
	public MemServiceSettings(){
	}
	

	@Override
	public String getDMServiceURL() {
		return dmServiceURL;
	}

	@Override
	public String getMSServiceURL() {
		return msServiceURL;
	}
	
	@Override
	public void setDMServiceURL(String dmServiceURL){
		this.dmServiceURL=dmServiceURL;
	}
	
	@Override
	public void setMSServiceURL(String msServiceURL){
		this.msServiceURL=msServiceURL;
	}

}
