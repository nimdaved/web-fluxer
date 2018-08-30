package org.ab.wfluxer.contacts.presentation.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class can be configured at run time when instantiated as Spring bean,
 * and annotated like: @ConfigurationProperties(prefix = "url")
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlConfig {

	private String contacts = "/contacts";
	private String addresses = "/addresses";
	private String functional = "/functional";
	private String functionalContacts = functional + contacts;
	private String ping = "/ping";

	private String paramEmail = "email";
	private String paramPhone = "phone";
	private String paramState = "state";
	private String paramCity = "city";

}
