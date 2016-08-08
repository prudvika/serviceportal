package zhulin.project.serviceportal.data;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
import zhulin.project.serviceportal.DeviceType;
import zhulin.project.serviceportal.web.DMServiceNotAvailableException;

@Component
public class DeviceTypeService implements DeviceTypeManager {
	private Client client = ClientBuilder.newClient();
	private ServiceSettings settings;

	public DeviceTypeService(ServiceSettings settings) {
		this.settings = settings;
	}

	@Override
	public List<DeviceType> loadDeviceTypes() {
		List<DeviceType> result = new ArrayList<DeviceType>();

		WebTarget base = client.target(settings.getDMServiceURL());
		Response response = base.path("dmrest/devicetypes").request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus() != 200) {
			throw new DMServiceNotAvailableException();
		}
		JsonArray array = response.readEntity(JsonArray.class);
		for (JsonValue value : array) {
			result.add(DeviceType.convertFromJsonObject((JsonObject) value));
		}

		return result;
	}

	@Override
	public DeviceType loadDeviceType(String deviceType) {
		WebTarget base = client.target(settings.getDMServiceURL());
		Response response = base.path("dmrest/devicetypes/type/" + deviceType).request(MediaType.APPLICATION_JSON)
				.get();
		if (response.getStatus() != 200) {
			throw new DMServiceNotAvailableException();
		}
		JsonObject object = response.readEntity(JsonObject.class);
		return DeviceType.convertFromJsonObject(object);
	}

	@Override
	public void addDeviceTypeAttributes(String deviceType, List<Attribute> attributes) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Attribute attribute : attributes) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
					.add("attribute", attribute.getName())
					.add("type", attribute.getType())
					.add("keepHistory", attribute.getKeepHistory());
			if (attribute.getDataField() != null) {
				objectBuilder.add("dataField", attribute.getDataField());
			}
			arrayBuilder.add(objectBuilder);
		}

		WebTarget base = client.target(settings.getDMServiceURL());
		Response response = base.path("dmrest/devicetypes/type/" + deviceType + "/attributes")
				.request(MediaType.APPLICATION_JSON).post(Entity.json(arrayBuilder.build()));
		System.out.println(String.format("Response for adding attributes for the device type %s : %d", deviceType,
				response.getStatus()));
		if (response.getStatus() != 200) {
			throw new DMServiceNotAvailableException();
		}
	}

	@Override
	public void updateDataFile(String deviceType, String dataFile) {
		WebTarget base = client.target(settings.getDMServiceURL());
		Response response = base.path("dmrest/devicetypes/type/" + deviceType + "/datafile")
				.request(MediaType.APPLICATION_JSON).post(Entity.text(dataFile));
		if (response.getStatus() != 200) {
			throw new DMServiceNotAvailableException();
		}

	}

	@Override
	public void createDeviceType(DeviceType deviceType) {
		System.out.println("Try to create the new device type:" + deviceType.getName());
		// Create the device type first
		WebTarget base = client.target(settings.getDMServiceURL());
		Response response = base.path("dmrest/devicetypes/type/" + deviceType.getName())
				.request(MediaType.APPLICATION_JSON).put(Entity.text(""));
		if (response.getStatus() != 200) {
			throw new DMServiceNotAvailableException();
		}

		// Set the data file
		System.out.println("Try to set the data file to " + deviceType.getDataFile());
		this.updateDataFile(deviceType.getName(), deviceType.getDataFile());
	}

}
