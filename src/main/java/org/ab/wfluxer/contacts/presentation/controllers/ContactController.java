package org.ab.wfluxer.contacts.presentation.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.ab.wfluxer.contacts.domain.entity.Contact;
import org.ab.wfluxer.contacts.domain.entity.ContactProjection;
import org.ab.wfluxer.contacts.domain.entity.IdHolder;
import org.ab.wfluxer.contacts.exception.ResourceNotFoundException;
import org.ab.wfluxer.contacts.service.ContactService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.contacts:/contacts}")
public class ContactController {
	private final ContactService contactService;

	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@GetMapping("${url.ping:/ping}")
	public @ResponseBody Map<String, String> ping() {
		return Collections.singletonMap("ping_message", "Hello from annotatated ContactController");
	}

	@GetMapping("/{id}")
	public @ResponseBody Contact getContact(@PathVariable("id") Long id) {
		return contactService.getContact(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find contact with id: " + id));
	}

	@PostMapping
	public @ResponseBody Contact createContact(@RequestBody @Valid Contact contactDetails) {
		return contactService.createContact(contactDetails);
	}

	@PutMapping
	public @ResponseBody Contact updateContact(@RequestBody @Valid Contact contactDetails) {
		return contactService.updateContact(contactDetails);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody IdHolder deleteContact(@PathVariable("id") Long id) {
		return contactService.deleteContact(id);
	}

	@GetMapping(produces = "application/json")
	public @ResponseBody List<ContactProjection> getContactsByEmailOrPhone(
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone) {
		return contactService.getContactsByEmailOrPhone(email, phone);
	}

	
}
