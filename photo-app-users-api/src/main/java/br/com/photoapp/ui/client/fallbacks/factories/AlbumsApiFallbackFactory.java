package br.com.photoapp.ui.client.fallbacks.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.photoapp.ui.client.fallbacks.AlbumsApiFallback;
import feign.hystrix.FallbackFactory;

@Component
public class AlbumsApiFallbackFactory implements FallbackFactory<AlbumsApiFallback> {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public AlbumsApiFallback create(Throwable cause) {
		logger.info(cause.getLocalizedMessage());
		
		return new AlbumsApiFallback();
	}

}
