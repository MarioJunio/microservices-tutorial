package br.com.photoapp.ui.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.photoapp.ui.model.UserEntity;
import br.com.photoapp.ui.security.dto.LoginDto;
import br.com.photoapp.ui.security.dto.UserSecurity;
import br.com.photoapp.ui.service.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private IUserService userService;
	private Environment env;

	public AuthenticationFilter(IUserService userService, Environment env, AuthenticationManager authManager) {
		this.userService = userService;
		this.env = env;
		super.setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

			System.out.println("-> Called AuthenticationFilter: " + loginDto.toString());

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginDto.getUsername(), loginDto.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER"))));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserSecurity userSecurity = (UserSecurity) authResult.getPrincipal();
		System.out.println("-> Login efetuado " + userSecurity.toString());
		
		UserEntity user = userService.getByEmail(userSecurity.getUsername());
		
		String jwt = Jwts.builder()
			.setSubject(user.getId().toString())
			.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(this.env.getProperty("app.jwt.expiration"))))
			.signWith(SignatureAlgorithm.HS512, this.env.getProperty("app.jwt.secret"))
			.compact();
		
		response.addHeader("user_id", user.getId().toString());
		response.addHeader(this.env.getProperty("app.jwt.identifier"), jwt);

	}

}
