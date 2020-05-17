package com.appsdeveloperblog.photoapp.api.albums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppApiAlbumsApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiAlbumsApplication.class, args);
	}

	@Bean
	public String beanDefault() {
		System.out.printf("-> Default Bean %s", env.getProperty("spring.rabbitmq.host"));
		return "default";
	}

	@Profile("prod")
	@Bean
	public String beanProduction() {
		System.out.printf("-> Production Bean %s", env.getProperty("spring.rabbitmq.host"));
		return "production";
	}

}
