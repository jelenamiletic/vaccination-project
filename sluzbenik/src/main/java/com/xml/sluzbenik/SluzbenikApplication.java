package com.xml.sluzbenik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SluzbenikApplication {

	public static void main(String[] args) {
		SpringApplication.run(SluzbenikApplication.class, args);
	}

}
