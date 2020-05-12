package br.com.photoapp.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import br.com.photoapp.ui.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;  

	@NotBlank(message = "First name is required")
	private String firstname;

	private String lastname;

	@NotBlank(message = "Email is required")
	@Email(message = "Email is invalid")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 3, message = "Password must have 3 caracters at least")
	private String password;
	
	@Transient
	private List<AlbumEntity> albums = new ArrayList<>();
	
	public UserDto toDto(ModelMapper mapper) {
		return mapper.map(this, UserDto.class);
	}
}
