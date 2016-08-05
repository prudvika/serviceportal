package zhulin.project.serviceportal.data;

import java.util.List;

import zhulin.project.serviceportal.Device;

public interface DeviceManager {
	public List<Device> loadDevices(String deviceType);
	public List<Device> loadDevices();
}
