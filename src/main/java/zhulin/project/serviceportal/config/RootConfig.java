package zhulin.project.serviceportal.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.*;

import zhulin.project.serviceportal.data.DashboardManager;
import zhulin.project.serviceportal.data.DashboardService;
import zhulin.project.serviceportal.data.DeviceManager;
import zhulin.project.serviceportal.data.DeviceService;
import zhulin.project.serviceportal.data.DeviceTypeManager;
import zhulin.project.serviceportal.data.DeviceTypeService;

@Configuration
public class RootConfig {
	@Bean
	public DeviceTypeManager deviceTypeManager(){
		return new DeviceTypeService("http://192.168.31.232:8080/devicemanager-1.0/");
	}
	
	@Bean
	public DeviceManager deviceManager(){
		return new DeviceService("http://192.168.31.232:8080/devicemanager-1.0/",
				deviceTypeManager());
	}
	
	@Bean
	public DashboardManager dashboardManager(){
		return new DashboardService("http://192.168.31.232:81/monitorservice-0.1/");
	}

}
