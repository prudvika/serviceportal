package zhulin.project.serviceportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("demo").password("www.i-qyy.com").roles("USER")
		.and().withUser("admin").password("zhulin").roles("USER","ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.formLogin().loginPage("/login")
		.and().logout().logoutSuccessUrl("/")
		.and().authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/mainpic.jpg").permitAll()
		.antMatchers("/style.css").permitAll()
		.antMatchers("/settings").hasRole("ADMIN")
		.anyRequest().authenticated();
	}
}
