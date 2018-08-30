package org.ab.wfluxer.contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.ab.wfluxer"})
public class WebFluxerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxerApplication.class, args);
	}
}
