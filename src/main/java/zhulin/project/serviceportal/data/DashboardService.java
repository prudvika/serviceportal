package zhulin.project.serviceportal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import zhulin.project.serviceportal.Dashboard;
import zhulin.project.serviceportal.DashboardAttribute;
import zhulin.project.serviceportal.DashboardAttributeMapping;
import zhulin.project.serviceportal.DashboardObjectMapping;
import zhulin.project.serviceportal.DashboardObject;
import zhulin.project.serviceportal.web.MonitorServiceNotAvailableException;

public class DashboardService implements DashboardManager {
	private WebTarget base;
	
	public DashboardService(String monitorServiceURL){
		Client client=ClientBuilder.newClient();
		this.base=client.target(monitorServiceURL);
	}
	
	@Override
	public void updateDashboard(Dashboard dashboard){
		
	}
	
	@Override
	public Dashboard loadDashboard(String dashboardName){
		Response response=base.path("dashrest/dashboards/dashboard/"+dashboardName)
				.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
		JsonObject object=response.readEntity(JsonObject.class);
		return convertFromJson(object);
	}

	@Override
	public List<Dashboard> loadDashboards() {
		List<Dashboard> dashboards=new ArrayList<Dashboard>();
		
		Response response=base.path("dashrest/dashboards").request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
		JsonArray array=response.readEntity(JsonArray.class);
		for(JsonValue value:array){
			dashboards.add(convertFromJson((JsonObject)value));
		}
		
		return dashboards;
	}
	
	private static Dashboard convertFromJson(JsonObject object){
		List<Integer> devices=new ArrayList<Integer>();
		for(JsonValue value:object.getJsonArray("devices")){
			devices.add(((JsonObject)value).getInt("deviceId"));
		}
		
		return new Dashboard(object.getString("name"),object.getString("author"),devices);
	}

	@Override
	public void createDashboard(Dashboard dashboard) {
		JsonObjectBuilder objectBuilder=Json.createObjectBuilder()
				.add("name", dashboard.getName())
				.add("author", dashboard.getAuthor());
		
		Response response=base.path("dashrest/dashboards/dashboard/"+dashboard.getName())
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(objectBuilder.build()));
		if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
	}

	@Override
	public DashboardObjectMapping loadDashboardMapping(String deviceType) {
		Response response=base.path("dashrest/config/mapping/"+deviceType)
				.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()==204){
			return null;
		}
		
		if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
		
		JsonObject object=response.readEntity(JsonObject.class);
		Map<String,List<DashboardAttributeMapping>> mappings=new HashMap<String,List<DashboardAttributeMapping>>();
		for(JsonValue value:object.getJsonArray("mappings")){
			String dashboardAttribute=((JsonObject)value).getString("dashboardAttribute");
			List<DashboardAttributeMapping> dashAttrMappings=new ArrayList<DashboardAttributeMapping>();
			JsonArray array=((JsonObject)value).getJsonArray("mappings");
			for(JsonValue value2:array){
				JsonObject object2=(JsonObject)value2;
				if(object2.containsKey("deviceAttribute")){
					dashAttrMappings.add(new DashboardAttributeMapping(dashboardAttribute
							,object2.getString("deviceAttribute")
							,object2.getString("operator")
							,object2.getString("deviceValue")
							,object2.getString("constantValue")));
				}else{
					dashAttrMappings.add(new DashboardAttributeMapping(dashboardAttribute
							,object2.getString("constantValue")));
				}
			}
			mappings.put(dashboardAttribute,dashAttrMappings);
		}
		return new DashboardObjectMapping(deviceType,object.getString("dashboardType"),mappings);
	}
	
	@Override
	public void saveDashboardMapping(DashboardObjectMapping objectMapping) {
		JsonObjectBuilder objectBuilder=Json.createObjectBuilder()
				.add("deviceType", objectMapping.getDeviceType())
				.add("dashboardType", objectMapping.getDashboardType());
		JsonArrayBuilder objectMappings=Json.createArrayBuilder();
		for(String dashboardAttribute:objectMapping.getMappings().keySet()){
			JsonObjectBuilder attributeMappings=Json.createObjectBuilder()
					.add("dashboardAttribute", dashboardAttribute);
			JsonArrayBuilder arrayBuilder=Json.createArrayBuilder();
			for(DashboardAttributeMapping dashboardAttributeMapping:objectMapping.getMappings().get(dashboardAttribute)){
				if(dashboardAttributeMapping.getDeviceAttribute()!=null&&dashboardAttributeMapping.getConstantValue()!=null){
					// Should be an expression
					arrayBuilder.add(Json.createObjectBuilder()
							.add("deviceAttribute", dashboardAttributeMapping.getDeviceAttribute())
							.add("operator", dashboardAttributeMapping.getOperator())
							.add("deviceValue", dashboardAttributeMapping.getDeviceValue())
							.add("constantValue", dashboardAttributeMapping.getConstantValue()));
				}else if(dashboardAttributeMapping.getDeviceAttribute()!=null){
					arrayBuilder.add(Json.createObjectBuilder()
							.add("deviceAttribute", dashboardAttributeMapping.getDeviceAttribute()));
					// Should only have 1
					break;
				}else{
					arrayBuilder.add(Json.createObjectBuilder()
							.add("constantValue", dashboardAttributeMapping.getConstantValue()));
				}
			}
			attributeMappings.add("mappings", arrayBuilder);
			objectMappings.add(attributeMappings);
		}
		objectBuilder.add("mappings", objectMappings);
		
		Response response=base.path("dashrest/config/mapping/"+objectMapping.getDeviceType())
				.request(MediaType.APPLICATION_JSON).post(Entity.json(objectBuilder.build()));
		if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
	}

	@Override
	public DashboardObject loadDashboardObject(String dashboardObject) {
		Response response=base.path("dashrest/config/dashboardobject/"+dashboardObject)
				.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()==204){
			return null;
		}else if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
		JsonObject object=response.readEntity(JsonObject.class);
		return this.convertJsonToDashboardObject(object);
	}
	
	@Override
	public List<DashboardObject> loadDashboardObjects() {
		List<DashboardObject> result=new ArrayList<DashboardObject>();
		
		Response response=base.path("dashrest/config/dashboardobjects")
				.request(MediaType.APPLICATION_JSON).get();
		if(response.getStatus()!=200){
			throw new MonitorServiceNotAvailableException();
		}
		JsonArray array=response.readEntity(JsonArray.class);
		for(JsonValue value:array){
			JsonObject object=(JsonObject)value;
			
			result.add(this.convertJsonToDashboardObject(object));
		}
		
		return result;
	}
	
	private DashboardObject convertJsonToDashboardObject(JsonObject object){
		String dashboardObjectName=object.getString("name");
		Set<DashboardAttribute> properties=new HashSet<DashboardAttribute>();
		for(JsonValue value2:object.getJsonArray("properties")){
			properties.add(new DashboardAttribute(
					((JsonObject)value2).getString("property"),
					((JsonObject)value2).getString("type")));
		}
		
		return new DashboardObject(dashboardObjectName,properties);
	}
}
