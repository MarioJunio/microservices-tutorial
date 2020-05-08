package br.com.photoapp.ui.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.photoapp.ui.client.fallbacks.factories.AlbumsApiFallbackFactory;
import br.com.photoapp.ui.dto.AlbumDto;

@FeignClient(name = "albums-api", fallbackFactory = AlbumsApiFallbackFactory.class)
public interface AlbumsApiClient {
	
	@GetMapping("/users/{id}/albums")
	public List<AlbumDto> userAlbums(@PathVariable String id);
}
