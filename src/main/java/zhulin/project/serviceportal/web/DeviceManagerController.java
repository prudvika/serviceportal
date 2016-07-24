package zhulin.project.serviceportal.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import zhulin.project.serviceportal.Attribute;
import zhulin.project.serviceportal.DeviceType;
import zhulin.project.serviceportal.data.DeviceManager;
import zhulin.project.serviceportal.data.DeviceTypeManager;

@Controller
@RequestMapping("/devicemanager")
public class DeviceManagerController {
	private DeviceTypeManager deviceTypeManager;
	private DeviceManager deviceManager;
	
	@Autowired
	public DeviceManagerController(DeviceTypeManager deviceTypeManager,DeviceManager deviceManager){
		this.deviceTypeManager=deviceTypeManager;
		this.deviceManager=deviceManager;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(@RequestParam(value="type", defaultValue="Test") String deviceType, Model model){
		model.addAttribute(deviceTypeManager.loadDeviceTypes());
		model.addAttribute(deviceManager.loadDevices(deviceType));
		
		return "devicemanager";
	}
	
	@RequestMapping(value="/devicetype",method=RequestMethod.GET)
	public String updateDeviceType(@RequestParam(value="type",defaultValue="Test") String deviceType,
			Model model){
		model.addAttribute(deviceTypeManager.loadDeviceTypes());
		model.addAttribute(new Attribute());
		model.addAttribute(new DeviceType());
		
		return "devicetypemanager";
	}
	
	@RequestMapping(value="/devicetype",method=RequestMethod.POST)
	public String createDeviceType(@Valid DeviceType deviceType, Errors errors){
		System.out.println("Got the request for creating the device type:"+deviceType.getName());
		if(errors.hasErrors()){
			return "devicetypemanager";
		}
		
		deviceTypeManager.createDeviceType(deviceType);
		
		return "redirect:/devicemanager/devicetype?type="+deviceType.getName();
	}
	
	@RequestMapping(value="/devicetype/{deviceType}/addattribute",method=RequestMethod.POST)
	public String addAttribute(@PathVariable String deviceType,@Valid Attribute attribute, Errors errors){
		if(errors.hasErrors()){
			return "devicetypemanager";
		}
		
		deviceTypeManager.addDeviceTypeAttribute(deviceType, attribute);
		
		return "redirect:/devicemanager/devicetype?type="+deviceType;
	}
	
	@RequestMapping(value="/devicetype/{deviceType}/datafile",method=RequestMethod.GET)
	public String updateDataFile(@PathVariable String deviceType,@RequestParam("dataFile") String dataFile){
		deviceTypeManager.updateDataFile(deviceType, dataFile);
		
		return "redirect:/devicemanager/devicetype?type="+deviceType;
	}
}
