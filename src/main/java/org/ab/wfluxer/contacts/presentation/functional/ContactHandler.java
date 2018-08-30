package org.ab.wfluxer.contacts.presentation.functional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Collections;

import org.ab.wfluxer.contacts.domain.entity.Contact;
import org.ab.wfluxer.contacts.service.ContactService;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * This is convenience class. All handler methods may be defined or "lambda
 * inlined" in the {@link ContactRouter}
 * 
 * @see ContactRouter
 *
 */
public class ContactHandler {

	private final ContactService contactService;

	public ContactHandler(ContactService contactService) {
		this.contactService = contactService;
	}

	public Mono<ServerResponse> handlePing(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON)
				.syncBody(Collections.singletonMap("ping_message", "Hello from functional ContactHandler"));
	}

	public Mono<ServerResponse> handleGetContactById(ServerRequest request) {
		final Long id = Long.valueOf(request.pathVariable("id"));

		return contactService.getContact(id).map(c -> ok().contentType(APPLICATION_JSON).syncBody(c))
				.orElse(ServerResponse.notFound().build());

		/**
		 * would contactService.getContact(id) return null, then: return
		 * ServerResponse.ok().contentType(APPLICATION_JSON).syncBody(contactService.getContact(id))
		 * .switchIfEmpty(ServerResponse.notFound().build());
		 */
	}

	public Mono<ServerResponse> handleCreateContact(ServerRequest request) {
		// TODO: try to simplify if possible
		return request.body(BodyExtractors.toMono(Contact.class))
				.flatMap(contact -> Mono.just(contactService.createContact(contact)))
				.flatMap(createdContact -> ok().contentType(APPLICATION_JSON).syncBody(createdContact));

	}

	public Mono<ServerResponse> handleUpdateContact(ServerRequest request) {
		// TODO: try to simplify if possible
		return request.body(BodyExtractors.toMono(Contact.class))
				.flatMap(contact -> Mono.just(contactService.updateContact(contact)))
				.flatMap(updatedContact -> ok().contentType(APPLICATION_JSON).syncBody(updatedContact));

	}

}
