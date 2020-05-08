package br.com.photoapp.ui.dto;

import org.modelmapper.ModelMapper;

import br.com.photoapp.ui.model.AlbumEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

	private String albumId;
	private String userId;
	private String name;
	private String description;
	
	public AlbumEntity toEntity(ModelMapper mapper) {
		return mapper.map(this, AlbumEntity.class);
	}
}
