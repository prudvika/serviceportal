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
import zhulin.project.serviceportal.data.MemServiceSettings;
import zhulin.project.serviceportal.data.ServiceSettings;

@Configuration
@ComponentScan(basePackages={"zhulin.project.serviceportal"},
    excludeFilters={@Filter(type=FilterType.ANNOTATION,value=EnableWebMvc.class)})
public class RootConfig {
	@Bean
	public ServiceSettings serviceSettings(){
		return new MemServiceSettings();
	}
	
	@Bean
	public DeviceTypeManager deviceTypeManager(ServiceSettings settings){
		return new DeviceTypeService(settings);
	}
	
	@Bean
	public DeviceManager deviceManager(ServiceSettings settings,DeviceTypeManager deviceTypeManager){
		return new DeviceService(settings,deviceTypeManager);
	}
	
	@Bean
	public DashboardManager dashboardManager(ServiceSettings settings){
		return new DashboardService(settings);
	}

}
