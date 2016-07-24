package zhulin.project.serviceportal;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeviceType {
	private int id;
	@NotNull
	@Size(min=1,max=20,message="{devicetype.name}")
	private String name;
	
	private Set<Attribute> attributes;
	private String dataFile;
	private Set<String> dataFields;

	public DeviceType() {
	}

	public DeviceType(int id, String name, Set<Attribute> attributes,String dataFile,Set<String> dataFields) {
		this.id = id;
		this.name = name;
		this.attributes = attributes;
		this.dataFile=dataFile;
		this.dataFields = dataFields;
	}

	public int getId() {
		return id;
	}

	public void setName(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public Set<Attribute> getAttributes() {
		return this.attributes;
	}
	
	public void setDataFile(String dataFile){
		this.dataFile=dataFile;
	}
	
	public String getDataFile(){
		return this.dataFile;
	}

	public Set<String> getDataFields() {
		return this.dataFields;
	}

	public static DeviceType convertFromJsonObject(JsonObject object) {
		JsonArray array = object.getJsonArray("attributes");
		Set<Attribute> attributes = new HashSet<Attribute>();
		for (JsonValue value : array) {
			JsonObject object2 = (JsonObject) value;
			attributes.add(new Attribute(object2.getString("attribute"), object2.getString("type"),
					object2.getBoolean("keepHistory"),
					object2.containsKey("dataField") ? object2.getString("dataField") : null));
		}

		String dataFile=object.containsKey("dataFile")?object.getString("dataFile"):null;
		
		Set<String> dataFields = null;
		if (object.containsKey("dataFields")) {
			array = object.getJsonArray("dataFields");
			dataFields = new HashSet<String>();
			for (JsonValue value : array) {
				dataFields.add(((JsonObject) value).getString("name"));
			}
		}

		return new DeviceType(object.getInt("id"), 
				object.getString("name"), attributes,dataFile,dataFields);
	}
}
