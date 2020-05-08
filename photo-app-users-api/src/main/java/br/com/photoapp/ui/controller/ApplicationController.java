package br.com.photoapp.ui.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

	private Environment env;

	@Autowired
	public ApplicationController(Environment env) {
		this.env = env;
	}

	@GetMapping("/infos")
	@ResponseStatus(HttpStatus.OK)
	public List<String> properties() {
		return Arrays.asList(env.getProperty("spring.application.name"), env.getProperty("local.server.port"),
				env.getProperty("app.jwt.secret"));
	}
}
