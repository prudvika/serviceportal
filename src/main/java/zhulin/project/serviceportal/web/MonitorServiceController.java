package zhulin.project.serviceportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import zhulin.project.serviceportal.Dashboard;
import zhulin.project.serviceportal.DashboardAttribute;
import zhulin.project.serviceportal.DashboardAttributeMapping;
import zhulin.project.serviceportal.DashboardObject;
import zhulin.project.serviceportal.DashboardObjectMapping;
import zhulin.project.serviceportal.data.DashboardManager;
import zhulin.project.serviceportal.data.DeviceManager;
import zhulin.project.serviceportal.data.DeviceTypeManager;

@Controller
@RequestMapping("/dashboardmanager")
public class MonitorServiceController {
	private DashboardManager dashboardManager;
	private DeviceTypeManager deviceTypeManager;
	private DeviceManager deviceManager;

	@Autowired
	public MonitorServiceController(DashboardManager dashboardManager, 
			DeviceTypeManager deviceTypeManager,DeviceManager deviceManager) {
		this.dashboardManager = dashboardManager;
		this.deviceTypeManager = deviceTypeManager;
		this.deviceManager=deviceManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute(dashboardManager.loadDashboards());
		model.addAttribute(new Dashboard());

		return "dashboardmanager";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createDashboard(@Valid Dashboard dashboard, Errors errors) {
		System.out.println("Got the request for creating the dashboard:" + dashboard.getName());
		if (errors.hasErrors()) {
			return "dashboardmanager";
		}

		dashboardManager.createDashboard(dashboard);

		return "redirect:/dashboardmanager";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String displayDashboardSettings(@RequestParam(value = "type", defaultValue = "Test") String deviceType,
			Model model) {
		model.addAttribute(deviceTypeManager.loadDeviceTypes());
		model.addAttribute(dashboardManager.loadDashboardObjects());
		DashboardObjectMapping mapping = dashboardManager.loadDashboardMapping(deviceType);
		if (mapping != null) {
			model.addAttribute(mapping);
		}

		return "dashboardsettings";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String updateDashboardSettings(@RequestParam(value = "type") String deviceType,
			@RequestParam(value = "dashboardtype") String dashboardType, HttpServletRequest request) {
		DashboardObject dashboardObject = this.dashboardManager.loadDashboardObject(dashboardType);
		
		Map<String,List<DashboardAttributeMapping>> objectMappings=new HashMap<String,List<DashboardAttributeMapping>>();
		for (DashboardAttribute dashboardAttribute : dashboardObject.getProperties()) {
			List<DashboardAttributeMapping> attributeMappings=new ArrayList<DashboardAttributeMapping>();
			for (int i = 0; i < 10; i++) { // 1 dashboard attribute can have 10
											// mappings in maximum
				String deviceAttribute = request.getParameter("device-" + dashboardAttribute.getName() + "-" + i);
				String operator = request.getParameter("operator-" + dashboardAttribute.getName() + "-" + i);
				String deviceValue = request.getParameter("value-" + dashboardAttribute.getName() + "-" + i);
				String constantValue = request.getParameter("constant-" + dashboardAttribute.getName() + "-" + i);
				if (deviceAttribute != null && !deviceAttribute.trim().equals("") && constantValue != null
						&& !constantValue.trim().equals("")) {
					attributeMappings.add(new DashboardAttributeMapping(dashboardAttribute.getName()
							,deviceAttribute,operator,deviceValue,constantValue));
				} else if (deviceAttribute != null && !deviceAttribute.trim().equals("")) {
					DashboardAttributeMapping dashboardAttributeMapping=new DashboardAttributeMapping(dashboardAttribute.getName(),null);
					dashboardAttributeMapping.setDeviceAttribute(deviceAttribute);
					attributeMappings.add(dashboardAttributeMapping);
					// Only the expression could be more than 1
					break;
				} else if (constantValue != null && !constantValue.trim().equals("")) {
					attributeMappings.add(new DashboardAttributeMapping(dashboardAttribute.getName()
							,constantValue));
					// Only the expression could be more than 1
					break;
				} else {
					break;
				}
			}
			objectMappings.put(dashboardAttribute.getName(), attributeMappings);
		}
		DashboardObjectMapping dashboardObjectMapping=new DashboardObjectMapping(
				deviceType,dashboardObject.getName(),objectMappings);
		dashboardManager.saveDashboardMapping(dashboardObjectMapping);
		
		return "redirect:/dashboardmanager/settings?type="+deviceType;
	}
	
	@RequestMapping(value = "/dashboardeditor", method = RequestMethod.GET)
	public String editDashboard(@RequestParam(value="name") String dashboardName,Model model){
		model.addAttribute(deviceTypeManager.loadDeviceTypes());
		model.addAttribute(deviceManager.loadDevices());
		model.addAttribute(dashboardManager.loadDashboard(dashboardName));
		
		return "dashboardeditor";
	}
	
	@RequestMapping(value="/dashboardeditor",method=RequestMethod.POST)
	public String editDashboard(@RequestParam(value="name") String dashboardName,
			HttpServletRequest request){
		String[] monitorDevices=request.getParameterValues("device");
		Dashboard dashboard=dashboardManager.loadDashboard(dashboardName);
		dashboard.getDevices().removeAll(dashboard.getDevices());
		for(String deviceId:monitorDevices){
			dashboard.getDevices().add(Integer.parseInt(deviceId));
		}
		
		return "redirect:/dashboardmanager/dashboardeditor?name="+dashboardName;
	}
}
