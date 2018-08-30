package org.ab.wfluxer.contacts.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;


//TODO: validation annotations
@Data
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope=Address.class)
public class Address implements Serializable {
	private static final long serialVersionUID = -656134523523252264L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String addressLine;

	private String city;

	private String state;

	private String postalCode;

}
