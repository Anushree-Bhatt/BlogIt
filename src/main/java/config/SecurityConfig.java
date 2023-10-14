	package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import customsecurity.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	private CustomUserDetailsService customUserDetailsService;
	
	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		// TODO Auto-generated constructor stub
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		auth.inMemoryAuthentication().withUser("oswald").password("oswald123").roles("ADMIN");
	//	auth.userDetailsService(userDetailsService());   -> inMemory
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	
	}
	
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable().
		authorizeRequests().
		antMatchers(HttpMethod.GET,"/api/**").permitAll().
		antMatchers("/api/auth/**").permitAll().
		antMatchers("/v3/api-docs/**").permitAll().
		antMatchers("/swagger-ui/**").permitAll().
		antMatchers("/swagger-ui.html/**").permitAll().
		antMatchers("/swagger-resources/**").permitAll().
		antMatchers("/webjars/**").permitAll().
		anyRequest().authenticated().and().formLogin().and().httpBasic();
	}
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//		UserDetails user1 = User.builder().username("nody").password(passwordEncoder().encode("nody123")).roles("ADMIN").build();
//		UserDetails user2 = User.builder().username("oswald").password(passwordEncoder().encode("oswald123")).roles("USER").build();
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	

}
