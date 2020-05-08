package br.com.photoapp.ui.client.fallbacks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.photoapp.ui.client.AlbumsApiClient;
import br.com.photoapp.ui.dto.AlbumDto;

public class AlbumsApiFallback implements AlbumsApiClient {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<AlbumDto> userAlbums(String id) {
		logger.info("AlbumsApiFallback.userAlbums is called");
		return new ArrayList<>();
	}

}
