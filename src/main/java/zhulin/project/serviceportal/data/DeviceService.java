package zhulin.project.serviceportal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import zhulin.project.serviceportal.Attribute;
import zhulin.project.serviceportal.Device;
import zhulin.project.serviceportal.DeviceHistory;
import zhulin.project.serviceportal.DeviceHistoryData;
import zhulin.project.serviceportal.DeviceType;
import zhulin.project.serviceportal.web.DMServiceNotAvailableException;

@Component
public class DeviceService implements DeviceManager {
	private DeviceTypeManager deviceTypeManager;
	private Client client=ClientBuilder.newClient();
	private ServiceSettings settings;
	
	public DeviceService(ServiceSettings settings,DeviceTypeManager deviceTypeManager){
		this.settings=settings;
		this.deviceTypeManager=deviceTypeManager;
	}
	
	@Override
	public Device loadDevice(int deviceId){
		WebTarget base=client.target(settings.getDMServiceURL());
		Response response=base.path("dmrest/devices/device/"+deviceId)
				.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()!=200){
			System.err.println("[Error]Device load service return code:"+response.getStatus());
			throw new DMServiceNotAvailableException();
		}
		JsonObject object=response.readEntity(JsonObject.class);
		
		return this.convertDeviceFromJson(object);
	}
	
	@Override
	public DeviceHistory loadDeviceHistory(int deviceId){
		Map<String,List<DeviceHistoryData>> historyData=new HashMap<String,List<DeviceHistoryData>>();
		WebTarget base=client.target(settings.getDMServiceURL());
		
		Device device=this.loadDevice(deviceId);
		DeviceType deviceType=deviceTypeManager.loadDeviceType(device.getType());
		for(Attribute deviceAttribute:deviceType.getAttributes()){
			if(deviceAttribute.getKeepHistory()){
				List<DeviceHistoryData> data=new ArrayList<DeviceHistoryData>();
				
				Response response=base.path("dmrest/devices/device/"+deviceId+"/history/"+deviceAttribute.getName())
				.request(MediaType.APPLICATION_JSON).get();
				if(response.getStatus()!=200){
					System.err.println("[Error]Load device history service return code:"+response.getStatus());
					throw new DMServiceNotAvailableException();
				}
				JsonArray array=response.readEntity(JsonArray.class);
				for(JsonValue value:array){
					JsonObject object=(JsonObject)value;
					data.add(new DeviceHistoryData(Long.parseLong(object.getString("time")),
							object.getString("value")));
				}
				
				historyData.put(deviceAttribute.getName(), data);
			}
		}
		
		
		return new DeviceHistory(deviceId,historyData);
	}
	
	@Override
	public void createDevice(Device device){
		WebTarget base=client.target(settings.getDMServiceURL());
		Response response=base.path("dmrest/devices/device").request(MediaType.APPLICATION_JSON)
				.post(Entity.json(device.toJSON()));
		if(response.getStatus()!=200){
			System.err.println("[Error]Device creation service return code:"+response.getStatus());
			throw new DMServiceNotAvailableException();
		}
	}
	
	@Override
	public void updateDevice(Device device){
		WebTarget base=client.target(settings.getDMServiceURL());
		Response response=base.path("dmrest/devices/device/"+device.getId())
				.request(MediaType.APPLICATION_JSON).post(Entity.json(device.toJSON()));
		if(response.getStatus()!=200){
			System.err.println("[Error]Device update service return code:"+response.getStatus());
			throw new DMServiceNotAvailableException();
		}
	}
	
	@Override
	public List<Device> loadDevices(){
		List<Device> result=new ArrayList<Device>();
		List<DeviceType> deviceTypes=deviceTypeManager.loadDeviceTypes();
		for(DeviceType deviceType:deviceTypes){
			result.addAll(loadDevices(deviceType.getName()));
		}
		
		return result;
	}

	@Override
	public List<Device> loadDevices(String deviceTypeName) {
		List<Device> result=new ArrayList<Device>();
		
		WebTarget base=client.target(settings.getDMServiceURL());
		Response response=base.path("dmrest/devices/type/"+deviceTypeName).request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()!=200){
			throw new DMServiceNotAvailableException();
		}
		
		JsonArray array=response.readEntity(JsonArray.class);
		for(JsonValue value:array){
			result.add(this.convertDeviceFromJson((JsonObject)value));
		}
		
		return result;
	}
	
	private Device convertDeviceFromJson(JsonObject object){
		Map<String,String> deviceAttributes=new HashMap<String,String>();
		DeviceType deviceType=deviceTypeManager.loadDeviceType(object.getString("type"));
		for(Attribute attribute:deviceType.getAttributes()){
			if(object.containsKey(attribute.getName())){
				deviceAttributes.put(attribute.getName(), object.getString(attribute.getName()));
			}
		}
		
		return new Device(object.getInt("id"),object.getString("name"),object.getString("type"),
				deviceAttributes);
	}

}
