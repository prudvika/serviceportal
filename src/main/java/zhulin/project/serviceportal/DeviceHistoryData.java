package zhulin.project.serviceportal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeviceHistoryData {
	private long time;
	private String value;
	
	public DeviceHistoryData(long time,String value){
		this.time=time;
		this.value=value;
	}
	
	public long getTime(){
		return this.time;
	}
	
	public String getTimeInFormat(){
		SimpleDateFormat sdf=new SimpleDateFormat();
		Date date=new Date(this.time);
		
		return sdf.format(date);
	}
	
	public String getValue(){
		return this.value;
	}
}
