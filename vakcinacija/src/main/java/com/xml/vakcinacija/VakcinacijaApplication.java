package com.xml.vakcinacija;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableAsync
public class VakcinacijaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VakcinacijaApplication.class, args);
	}

}
