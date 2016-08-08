package zhulin.project.serviceportal.data;

import java.util.List;

import zhulin.project.serviceportal.Device;
import zhulin.project.serviceportal.DeviceHistory;


public interface DeviceManager {
	public List<Device> loadDevices(String deviceType);
	public List<Device> loadDevices();
	
	public Device loadDevice(int deviceId);
	public DeviceHistory loadDeviceHistory(int deviceId);
	public void createDevice(Device device);
	public void updateDevice(Device device);
}
