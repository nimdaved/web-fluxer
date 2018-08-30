package org.ab.wfluxer.contacts;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.ab.wfluxer.contacts.domain.entity.Contact;
import org.ab.wfluxer.contacts.presentation.util.UrlConfig;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class ContactControllerTest {

	@Autowired
	UrlConfig urls;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testAnnotatedControllerCreateAndGet() throws Exception {
		testCreateAndGet(urls.getContacts());
	}

	@Test
	public void testFunctionalControllerCreateAndGet() throws Exception {
		testCreateAndGet(urls.getFunctionalContacts());
	}

	public void testCreateAndGet(String urlRoot) throws Exception {

		final String URI = urlRoot + "/";
		Contact toSave = createContactTemplate();
		// create
		ResponseSpec ra = webTestClient.post().uri(urlRoot).contentType(MediaType.APPLICATION_JSON).syncBody(toSave)
				.exchange().expectStatus().isOk();
		Contact saved = toDomainObject(ra, Contact.class);
		assertNotNull(saved.getId());
		assertNotEquals(toSave.getId(), saved.getId());
		// makes sure photo was not corrupted
		assertArrayEquals(toSave.getProfileImage(), saved.getProfileImage());

		// retrieve
		ra = webTestClient.get().uri(URI + saved.getId()).exchange().expectStatus().isOk();
		Contact retrieved = toDomainObject(ra, Contact.class);
		// check if the same as saved
		assertEquals(saved, retrieved);
	}

	private Contact createContactTemplate() {
		Contact c = new Contact();
		c.setName("John Doe");
		c.setEmail("a@a.com");
		c.setPhoneNumberHome("9998887777");
		try {
			byte[] b = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("./yet_another_pic.jpeg"));
			c.setProfileImage(b);
		} catch (IOException e) {
			throw new IllegalStateException("Could not load resource.", e);
		}

		return c;
	}

	private <T> T toDomainObject(ResponseSpec r, Class<T> domainClass) throws IOException {
		return r.expectBody(domainClass).returnResult().getResponseBody();
	}

	public String toJson(Object o) throws JsonProcessingException {
		return objectMapper.writeValueAsString(o);
	}

}
