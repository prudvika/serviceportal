package zhulin.project.serviceportal.data;

import java.util.List;


import zhulin.project.serviceportal.Attribute;
import zhulin.project.serviceportal.DeviceType;

public interface DeviceTypeManager {
	public List<DeviceType> loadDeviceTypes();
	public DeviceType loadDeviceType(String deviceType);
	public void addDeviceTypeAttributes(String deviceType,List<Attribute> attributes);
	public void updateDataFile(String deviceType,String dataFile);
	public void createDeviceType(DeviceType deviceType);
}
