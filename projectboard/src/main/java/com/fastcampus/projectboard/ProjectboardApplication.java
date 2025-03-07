package com.fastcampus.projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ProjectboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectboardApplication.class, args);
	}
}
