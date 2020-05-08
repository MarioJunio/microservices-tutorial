package br.com.photoapp.ui.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.photoapp.ui.dto.UserDto;
import br.com.photoapp.ui.model.UserEntity;
import br.com.photoapp.ui.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private ModelMapper mapper;
	
	private IUserService userService;

	@Autowired
	public UserController(ModelMapper mapper, IUserService userService) {
		this.mapper = mapper;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto user) {
		UserEntity userCreated = userService.create(user);

		return ResponseEntity.created(null).body(mapper.map(userCreated, UserDto.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUser(@PathVariable Long id) {
		UserEntity userEntity = userService.getByIdWithAlbums(id);

		return ResponseEntity.ok(userEntity.toDto(mapper));
	}

}
