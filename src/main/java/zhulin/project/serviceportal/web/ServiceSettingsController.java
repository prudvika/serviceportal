package zhulin.project.serviceportal.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zhulin.project.serviceportal.data.ServiceSettings;

@Controller
@RequestMapping("/settings")
public class ServiceSettingsController {
	private ServiceSettings settings;
	
	@Autowired
	public ServiceSettingsController(ServiceSettings settings){
		this.settings=settings;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("dmServiceURL",settings.getDMServiceURL());
		model.addAttribute("msServiceURL",settings.getMSServiceURL());
		return "settings";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String updateSettings(HttpServletRequest request){
		settings.setDMServiceURL(request.getParameter("dmServiceURL"));
		settings.setMSServiceURL(request.getParameter("msServiceURL"));
		
		return "redirect:/settings";
	}
}
