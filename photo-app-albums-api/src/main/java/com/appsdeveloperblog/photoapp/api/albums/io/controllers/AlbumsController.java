package com.appsdeveloperblog.photoapp.api.albums.io.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.photoapp.api.albums.data.AlbumEntity;
import com.appsdeveloperblog.photoapp.api.albums.service.AlbumsService;
import com.appsdeveloperblog.photoapp.api.albums.ui.model.AlbumResponseModel;

@RestController
public class AlbumsController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AlbumsService albumsService;

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> userAlbums(@PathVariable String id) {
		List<AlbumResponseModel> returnValue = new ArrayList<>();

		List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);

		if (albumsEntities.isEmpty())
			return returnValue;

		Type listType = new TypeToken<List<AlbumResponseModel>>() {
		}.getType();

		returnValue = new ModelMapper().map(albumsEntities, listType);
		
		logger.info("Returning " + returnValue.size() + " albums");
		
		return returnValue;
	}
}
