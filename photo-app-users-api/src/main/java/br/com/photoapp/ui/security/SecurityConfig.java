package br.com.photoapp.ui.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.photoapp.ui.security.filter.AuthenticationFilter;
import br.com.photoapp.ui.service.IUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Environment env;
	private IUserService userService;
	private BCryptPasswordEncoder passwordEnconder;

	@Autowired
	public SecurityConfig(IUserService userService, Environment env, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.env = env;
		this.passwordEnconder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests()
			.antMatchers("/**").permitAll()
		.and()
			.addFilter(getAuthenticationFilter());

		http.headers().frameOptions().disable();

	}

	private Filter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(this.userService, this.env, authenticationManager());
		authenticationFilter.setFilterProcessesUrl(this.env.getProperty("app.login.url"));
		
		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEnconder);
	}

}
