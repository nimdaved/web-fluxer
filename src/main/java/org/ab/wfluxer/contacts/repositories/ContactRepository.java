package org.ab.wfluxer.contacts.repositories;

import java.util.List;

import org.ab.wfluxer.contacts.domain.entity.Contact;
import org.ab.wfluxer.contacts.domain.entity.ContactProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	List<ContactProjection> findByEmailOrPhoneNumberWorkOrPhoneNumberHome(String email, String phone, String phone2);

	List<ContactProjection> findByEmail(String email);

	List<ContactProjection> findByPhoneNumberWorkOrPhoneNumberHome(String phone, String phone2);
	

}
