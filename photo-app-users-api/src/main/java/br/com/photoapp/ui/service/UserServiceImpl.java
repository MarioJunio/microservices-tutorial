package br.com.photoapp.ui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.photoapp.ui.client.AlbumsApiClient;
import br.com.photoapp.ui.dto.AlbumDto;
import br.com.photoapp.ui.dto.UserDto;
import br.com.photoapp.ui.model.AlbumEntity;
import br.com.photoapp.ui.model.UserEntity;
import br.com.photoapp.ui.repository.UserRepository;
import br.com.photoapp.ui.security.dto.UserSecurity;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private ModelMapper mapper;
	private UserRepository repository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	private RestTemplate restTemplate;
	private AlbumsApiClient albumsApiClient;
	private Environment env;

	@Autowired
	public UserServiceImpl(ModelMapper mapper, UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,
			RestTemplate restTemplate, AlbumsApiClient albumsApiClient, Environment env) {
		this.mapper = mapper;
		this.repository = repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//		this.restTemplate = restTemplate;
		this.albumsApiClient = albumsApiClient;
		this.env = env;
	}

	@Override
	public UserEntity create(UserDto userDto) {
		UserEntity user = mapper.map(userDto, UserEntity.class);
		user.setUserId(UUID.randomUUID().toString());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		return repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = getByEmail(username);

		return new UserSecurity(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public UserEntity getByEmail(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userOp = repository.findByEmail(username);

		return userOp.orElseThrow(() -> createUsernameNotFoundException());
	}

	@Override
	public UserEntity getByIdWithAlbums(Long id) {
		Optional<UserEntity> getUser = repository.findById(id);

		UserEntity user = getUser.orElseThrow(() -> createUsernameNotFoundException());

		/*
		 * ResponseEntity<List<AlbumDto>> albumsDtoResponse =
		 * this.restTemplate.exchange(
		 * String.format(this.env.getProperty("albums-api.find-by-user-url"),
		 * String.valueOf(id)), HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumDto>>() { });
		 */
		
		logger.info("Before call userAlbums");
		List<AlbumDto> albumsDto = this.albumsApiClient.userAlbums(String.valueOf(id));
		logger.info("After call userAlbums");	
		
		user.setAlbums(toAlbumEntity(albumsDto));

		return user;

	}

	private UsernameNotFoundException createUsernameNotFoundException() {
		return new UsernameNotFoundException("Usuario não encontrado");
	}

	private List<AlbumEntity> toAlbumEntity(List<AlbumDto> albums) {
		return albums.stream().map((albumDto) -> albumDto.toEntity(mapper)).collect(Collectors.toList());
	}
}
