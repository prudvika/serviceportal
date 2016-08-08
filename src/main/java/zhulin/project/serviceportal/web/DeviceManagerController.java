package zhulin.project.serviceportal.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import zhulin.project.serviceportal.Device;
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
		model.addAttribute(new Device());
		
		return "devicemanager";
	}
	
	private Map<String,String> retrieveDeviceAttributes(HttpServletRequest request){
		Map<String,String> deviceAttributes=new HashMap<String,String>();
		Enumeration<String> parameters=request.getParameterNames();
		while(parameters.hasMoreElements()){
			String parameterName=(String)parameters.nextElement();
			if(parameterName.equals("name")||parameterName.equals("type")
					||parameterName.equals("id")){
				continue;
			}else{
				if(!request.getParameter(parameterName).trim().equals("")){
					deviceAttributes.put(parameterName, request.getParameter(parameterName));
				}
			}
		}
		
		return deviceAttributes;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addDevice(@RequestParam(value="type") String deviceType,
			@Valid Device device, Errors errors, HttpServletRequest request){
		if(errors.hasErrors()){
			return "devicemanager";
		}
		
		device.setType(deviceType);
		device.setAttributes(this.retrieveDeviceAttributes(request));
		deviceManager.createDevice(device);
		
		return "redirect:/devicemanager?type="+deviceType;
	}
	
	@RequestMapping(value="/device",method=RequestMethod.GET)
	public String viewDevice(@RequestParam(value="id")int deviceId,Model model){
		model.addAttribute(deviceManager.loadDevice(deviceId));
		model.addAttribute(deviceManager.loadDeviceHistory(deviceId));
		
		
		return "device";
	}
	
	@RequestMapping(value="/device",method=RequestMethod.POST)
	public String updateDevice(@RequestParam(value="id") int deviceId,
			@Valid Device device, Errors errors, HttpServletRequest request){
		if(errors.hasErrors()){
			return "devicemanager";
		}
		
		device.setId(deviceId);
		device.setAttributes(this.retrieveDeviceAttributes(request));
		deviceManager.updateDevice(device);
		
		return "redirect:/devicemanager/device?id="+deviceId;
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
	
	@RequestMapping(value="/devicetype/{deviceType}/addattributes",method=RequestMethod.POST)
	public String addAttribute(@PathVariable String deviceType,HttpServletRequest request){
		List<Attribute> attributes=new ArrayList<Attribute>();
		
		int i=1;
		while(request.getParameter("name-"+i)!=null&&
				!request.getParameter("name-"+i).trim().equals("")){
			String name=request.getParameter("name-"+i);
			String type=request.getParameter("type-"+i);
			boolean keepHistory=request.getParameter("keepHistory-"+i)==null?false:true;
			String dataField=request.getParameter("dataField-"+i);
			
			attributes.add(new Attribute(name,type,keepHistory,dataField));
			i++;
		}
		
		deviceTypeManager.addDeviceTypeAttributes(deviceType, attributes);
		
		return "redirect:/devicemanager/devicetype?type="+deviceType;
	}
	
	@RequestMapping(value="/devicetype/{deviceType}/datafile",method=RequestMethod.GET)
	public String updateDataFile(@PathVariable String deviceType,@RequestParam("dataFile") String dataFile){
		deviceTypeManager.updateDataFile(deviceType, dataFile);
		
		return "redirect:/devicemanager/devicetype?type="+deviceType;
	}
}
