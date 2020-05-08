package br.com.photoapp.zuul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import br.com.photoapp.zuul.security.filter.AuthorizationFilter;

@Order(value = 101)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Environment env;
	
	@Autowired
	public SecurityConfig(Environment env) {
		this.env = env;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable();
		
		http.authorizeRequests()
			.antMatchers(env.getProperty("app.h2console.url")).permitAll()
			.antMatchers(env.getProperty("app.actuator.zuul.url")).permitAll()
			.antMatchers(env.getProperty("app.actuator.users-api.url")).permitAll()
			.antMatchers(HttpMethod.POST, env.getProperty("app.registration.url")).permitAll()
			.antMatchers(HttpMethod.POST, "/users-api/" + env.getProperty("app.login.url")).permitAll()
			.anyRequest().authenticated()
		.and()
			.addFilter(new AuthorizationFilter(authenticationManager(), this.env));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
