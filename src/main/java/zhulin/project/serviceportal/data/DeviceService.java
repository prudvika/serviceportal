package zhulin.project.serviceportal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import zhulin.project.serviceportal.Attribute;
import zhulin.project.serviceportal.Device;
import zhulin.project.serviceportal.DeviceType;

@Component
public class DeviceService implements DeviceManager {
	private String dmServerURL;
	private DeviceTypeManager deviceTypeManager;
	
	public DeviceService(String dmServerURL,DeviceTypeManager deviceTypeManager){
		this.dmServerURL=dmServerURL;
		this.deviceTypeManager=deviceTypeManager;
	}

	@Override
	public List<Device> loadDevices(String deviceTypeName) {
		List<Device> result=new ArrayList<Device>();
		DeviceType deviceType=deviceTypeManager.loadDeviceType(deviceTypeName);
		
		Client client=ClientBuilder.newClient();
		WebTarget base=client.target(this.dmServerURL);
		
		Response response=base.path("dmrest/devices/type/"+deviceTypeName).request(MediaType.APPLICATION_JSON).get();
		JsonArray array=response.readEntity(JsonArray.class);
		for(JsonValue value:array){
			JsonObject object=(JsonObject)value;
			Map<String,String> deviceAttributes=new HashMap<String,String>();
			for(Attribute attribute:deviceType.getAttributes()){
				if(object.containsKey(attribute.getName())){
					deviceAttributes.put(attribute.getName(), object.getString(attribute.getName()));
				}
			}
			
			result.add(new Device(object.getInt("id"),object.getString("name"),deviceAttributes));
		}
		
		return result;
	}

}
