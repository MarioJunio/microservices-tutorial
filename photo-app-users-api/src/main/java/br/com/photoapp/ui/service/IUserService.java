package br.com.photoapp.ui.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.photoapp.ui.dto.UserDto;
import br.com.photoapp.ui.model.UserEntity;

public interface IUserService extends UserDetailsService {

	UserEntity create(UserDto user);
	
	UserEntity getByEmail(String username);
	
	UserEntity getByIdWithAlbums(Long id);
	
}
