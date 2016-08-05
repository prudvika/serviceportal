package zhulin.project.serviceportal;

public class DashboardAttribute {
	private String name;
	private String type;
	
	public DashboardAttribute(String name,String type){
		this.name=name;
		this.type=type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof DashboardAttribute){
			DashboardAttribute attribute=(DashboardAttribute)obj;
			
			if(attribute.getName().equals(this.name)&&attribute.getType().equals(this.type)){
				return true;
			}
		}
		
		return false;
	}
}
