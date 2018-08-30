package org.ab.wfluxer.contacts.repositories;

import org.ab.wfluxer.contacts.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
