package org.ab.wfluxer.contacts.presentation.functional;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.ab.wfluxer.contacts.presentation.util.UrlConfig;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class ContactRouter {
	private final ContactHandler contactHandler;
	private final UrlConfig urls;

	public ContactRouter(ContactHandler contactHandler, UrlConfig urls) {
		this.contactHandler = contactHandler;
		this.urls = urls;
	}

	/**
	 * This is optional method that combines all routes Each route may be
	 * implemented as separated method. In this case individual routes need to be
	 * registered with server similar to
	 * {@link org.ab.wfluxer.contacts.configuration.WebFluxerConfiguration#routes()}
	 * 
	 * @return combined
	 *         {@link org.springframework.web.reactive.function.server.RouterFunction}
	 */
	public RouterFunction<ServerResponse> routes() {
		final RouterFunction<ServerResponse> childRoutes = RouterFunctions
				.route(GET(urls.getPing()), contactHandler::handlePing)
				// use '.and(...) if need separate method per route
				.andRoute(GET("/{id}").and(accept(APPLICATION_JSON)), contactHandler::handleGetContactById)
				.andRoute(POST(""), contactHandler::handleCreateContact)
				.andRoute(PUT(""), contactHandler::handleUpdateContact);
		
		//nest(..) allows to use single top level url root
		return RouterFunctions.nest(RequestPredicates.path(urls.getFunctionalContacts()), childRoutes);
	}

}
