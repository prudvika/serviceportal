package zhulin.project.serviceportal;

import java.util.List;
import java.util.Map;

public class DeviceHistory {
	private int deviceId;
	private Map<String,List<DeviceHistoryData>> historyData;
	
	public DeviceHistory(int deviceId,Map<String,List<DeviceHistoryData>> historyData){
		this.deviceId=deviceId;
		this.historyData=historyData;
	}
	
	public int getDeviceId(){
		return this.deviceId;
	}
	
	public Map<String,List<DeviceHistoryData>> getHistoryData(){
		return this.historyData;
	}
}
