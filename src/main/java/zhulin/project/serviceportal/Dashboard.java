package zhulin.project.serviceportal;

import java.util.List;

public class Dashboard {
	private String name;
	private String author;
	private List<Integer> devices;
	
	public Dashboard(){
	}
	
	public Dashboard(String name,String author,List<Integer> devices){
		this.name=name;
		this.author=author;
		this.devices=devices;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public void setAuthor(String author){
		this.author=author;
	}
	
	public List<Integer> getDevices(){
		return this.devices;
	}
}
