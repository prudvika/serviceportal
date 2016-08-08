package zhulin.project.serviceportal.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.*;
import org.springframework.web.servlet.view.tiles3.*;

@Configuration
@EnableWebMvc
@ComponentScan("zhulin.project.serviceportal.web")
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver() {
		return new TilesViewResolver();
	}
	
	@Bean
	public TilesConfigurer tileConfigurer(){
		TilesConfigurer tiles=new TilesConfigurer();
		tiles.setDefinitions(new String[]{
				"/WEB-INF/layout/tiles.xml"
		});
		tiles.setCheckRefresh(true);
		
		return tiles;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
}