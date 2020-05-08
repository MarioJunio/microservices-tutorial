package br.com.photoapp.ui.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserSecurity extends User {

	public UserSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return super.toString();
	}

}
