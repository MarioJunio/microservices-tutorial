package br.com.photoapp.ui.client.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignExceptionHandler implements ErrorDecoder {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Exception decode(String methodKey, Response response) {

		logger.info("FeignExceptionHandler: " + response.status());

		switch (response.status()) {
		case 400:
			break;
		case 404:
			return new ResponseStatusException(HttpStatus.valueOf(response.status()),
					String.format("method: %s calls URL that does not found", methodKey));
		default:
			return new Exception(response.reason());
		}

		return null;
	}

}
