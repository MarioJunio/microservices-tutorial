package br.com.photoapp.ui.security.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username; 
	private String password;
}
