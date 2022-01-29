package pkabus.comuniostatsbackend.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pkabus.comuniostatsbackend.web.dto.ClubDto;

@SpringBootTest
public class CreateClubsIntegrationTest {

	@Autowired
	private ClubController clubController;

	@Test
	void create_18_bundesliga_clubs_with_different_names_thenSuccess() {
		List<ClubDto> clubs = IntStream.range(0, 18) //
				.mapToObj(i -> new ClubDto(randomAlphabetic(9))) //
				.collect(Collectors.toList());

		clubs.forEach(clubController::create);

		List<ClubDto> dtosByName = clubs.stream() //
				.map(ClubDto::getName) //
				.map(clubController::byName) //
				.collect(Collectors.toList());

		assertThat(dtosByName).usingElementComparatorIgnoringFields("id").containsAll(clubs);
	}

	@Test
	void create_2_clubs_with_same_name_expectOnly1Club_thenSuccess() {
		ClubDto club = new ClubDto(randomAlphabetic(6));

		clubController.create(club);
		clubController.create(club);

		assertThat(clubController.all(0, 9999).getContent()).usingElementComparatorIgnoringFields("id")
				.containsOnlyOnce(club);
	}
}
