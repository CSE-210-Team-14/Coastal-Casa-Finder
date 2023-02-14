package com0.coastalcasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com0.coastalcasa.Repo")
public class CoastalCasaFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoastalCasaFinderApplication.class, args);
	}

}
