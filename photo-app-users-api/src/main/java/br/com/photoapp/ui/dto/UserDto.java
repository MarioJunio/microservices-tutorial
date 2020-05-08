package br.com.photoapp.ui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"passwordÏ"})
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String userId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private List<AlbumDto> albums = new ArrayList<>();

}
