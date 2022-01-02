package pkabus.comuniostatsbackend.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.web.dto.ClubDto;

// @WebMvcTest // application context will load web related beans, but no services/repos/entity
// beans
@SpringBootTest
public class ClubControllerIntegrationTest {

	@Autowired
	private ClubController clubController;

	@Test
	void givenClub_deleteByName_thenSuccess() {
		String name = randomAlphabetic(6);
		ClubDto clubDto = new ClubDto(name);
		clubController.create(clubDto);

		ClubDto byNameBefore = clubController.byName(name);
		assertThat(byNameBefore).usingRecursiveComparison() //
				.ignoringFields("id").isEqualTo(clubDto);

		clubController.deleteByName(name);

		assertThatThrownBy(() -> clubController.byName(name)) //
				.isInstanceOf(ResponseStatusException.class);
	}
}
