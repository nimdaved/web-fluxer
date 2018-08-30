package org.ab.wfluxer.contacts.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.ab.wfluxer.contacts.domain.entity.Contact;
import org.ab.wfluxer.contacts.domain.entity.ContactProjection;
import org.ab.wfluxer.contacts.domain.entity.IdHolder;
import org.ab.wfluxer.contacts.exception.BadRequestException;
import org.ab.wfluxer.contacts.exception.ResourceNotFoundException;
import org.ab.wfluxer.contacts.repositories.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactService {

	private final ContactRepository contactRepository;

	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	public Optional<Contact> getContact(Long id) {

		return contactRepository.findById(id);
	}

	public IdHolder deleteContact(Long id) {
		contactRepository.deleteById(id);
		return new IdHolder(id);

	}

	public Contact createContact(Contact contactDetails) {
		return contactRepository.save(contactDetails);
	}

	public Contact updateContact(Contact contactDetails) {
		return contactRepository.save(contactDetails);
	}

	public List<ContactProjection> getContactsByEmailOrPhone(String email, String phone) {
		List<ContactProjection> contacts = null;
		if (email != null && phone != null) {
			contacts = contactRepository.findByEmailOrPhoneNumberWorkOrPhoneNumberHome(email, phone, phone);
		} else if (email != null) {
			contacts = contactRepository.findByEmail(email);
		} else if (phone != null) {
			contacts = contactRepository.findByPhoneNumberWorkOrPhoneNumberHome(phone, phone);
		} else {
			throw missingSingleParameter("email", "phone");
		}
		return notNull(contacts);
	}

	private static BadRequestException missingSingleParameter(String... names) {
		return new BadRequestException(
				"At least single parameter from " + Arrays.asList(names) + " should be present.");
	}

	private static <T> T notNull(T object) {
		return notNull(Optional.ofNullable(object));
	}

	private static <T> T notNull(Optional<T> object) {
		return object.orElseThrow(() -> new ResourceNotFoundException("Entry is not found"));
	}

	
}
