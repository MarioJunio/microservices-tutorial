package br.com.photoapp.zuul.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

@RefreshScope
public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	private Environment env;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.env = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorization = request.getHeader(this.env.getProperty("app.authorization.name"));

		// se não foi informado a autorização continue a execução da cadeia de filtros
		if (StringUtils.isEmpty(authorization)) {
			chain.doFilter(request, response);
		} else {
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));
			chain.doFilter(request, response);
		}

	}

	private Authentication getAuthentication(String authorization) {

		// remove o prefixo do token
		String jwt = authorization.replace(this.env.getProperty("app.authorization.prefix"), "");
		
		// se houver um token então crie uma credencial
		if (StringUtils.isNotEmpty(authorization)) {
			String userId = Jwts.parser().setSigningKey(this.env.getProperty("app.jwt.secret")).parseClaimsJws(jwt)
					.getBody().getSubject();
			
			return new UsernamePasswordAuthenticationToken(userId, null,
					Arrays.asList(new SimpleGrantedAuthority("USER")));
		}

		return null;
	}

}
