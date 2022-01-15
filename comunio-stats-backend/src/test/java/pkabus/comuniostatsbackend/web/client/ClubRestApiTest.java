package pkabus.comuniostatsbackend.web.client;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static pkabus.comuniostatsbackend.web.controller.ClubController.BASE_CLUBS;

import java.util.Collection;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.core.TypeReferences.PagedModelType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pkabus.comuniostatsbackend.web.dto.ClubDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClubRestApiTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void givenClub_whenByNameContains_then200Ok_and_thenExpectExactlyOne() {
		String name = randomAlphabetic(16);

		ClubDto clubDto = new ClubDto(name);
		restTemplate.withBasicAuth("crawler", "password").postForEntity(BASE_CLUBS, clubDto, Void.class);

		ResponseEntity<PagedModel<ClubDto>> responseList = restTemplate.exchange(
				BASE_CLUBS + "/q?name=" + name.substring(3, 14), HttpMethod.GET, null, new PagedModelType<ClubDto>() {
				});

		Collection<ClubDto> clubs = responseList.getBody().getContent();

		SoftAssertions softAssertions = new SoftAssertions();

		softAssertions.assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);
		softAssertions.assertThat(clubs).usingElementComparatorIgnoringFields("id").containsExactly(clubDto);

		softAssertions.assertAll();
	}
}
