package org.ab.wfluxer.contacts.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

//TODO: validation annotations
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Contact.class)
public class Contact implements Serializable, ContactProjection {

	private static final long serialVersionUID = 1328095975855166767L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String company;
	private String email;
	private String phoneNumberWork;
	private String phoneNumberHome;
	

	@Lob
	private byte[] profileImage;

}
