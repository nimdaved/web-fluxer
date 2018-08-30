package org.ab.wfluxer.contacts.configuration;

import org.ab.wfluxer.contacts.presentation.functional.ContactHandler;
import org.ab.wfluxer.contacts.presentation.functional.ContactRouter;
import org.ab.wfluxer.contacts.presentation.util.UrlConfig;
import org.ab.wfluxer.contacts.service.ContactService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * This configuration, besides other bean initialization: a. Enables URL
 * configuration in the application context b. automatically plugs routes into
 * server
 * 
 * This can be substituted with 0-annotation code similar to
 * "startReactorServer()" in
 * https://github.com/poutsma/web-function-sample/blob/master/src/main/java/org/springframework/samples/web/reactive/function/Server.java
 */
@Configuration
public class WebFluxerConfiguration {

	@ConfigurationProperties(prefix = "url")
	@Bean
	UrlConfig urls() {
		return new UrlConfig();
	}

	@Bean
	ContactHandler contactHandler(ContactService contactService) {
		return new ContactHandler(contactService);
	}

	@Bean
	RouterFunction<ServerResponse> routes(ContactHandler contactHandler, UrlConfig urls) {
		return new ContactRouter(contactHandler, urls).routes();
	}

}
