package zhulin.project.serviceportal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Attribute {
	@NotNull
	@Size(min=1,max=20,message="{attribute.name}")
	private String name;
	
	@NotNull
	@Size(min=1,max=20,message="{attribute.type}")
	private String type;
	
	private boolean keepHistory;
	private String dataField;
	
	public Attribute(){
	}
	
	public Attribute(String name,String type,boolean keepHistory,String dataField){
		this.name=name;
		this.type=type;
		this.keepHistory=keepHistory;
		this.dataField=dataField;
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
		return this.type;
	}
	
	public void setKeepHistory(boolean keepHistory){
		this.keepHistory=keepHistory;
	}
	public boolean getKeepHistory(){
		return this.keepHistory;
	}
	
	public String getDataField(){
		return this.dataField;
	}
	public void setDataField(String dataField){
		this.dataField=dataField;
	}
}
