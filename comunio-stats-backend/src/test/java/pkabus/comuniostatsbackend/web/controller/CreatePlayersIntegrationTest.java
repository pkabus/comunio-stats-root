package pkabus.comuniostatsbackend.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pkabus.comuniostatsbackend.web.dto.PlayerDto;

@SpringBootTest
public class CreatePlayersIntegrationTest {

	@Autowired
	private PlayerController playerController;

	@AfterEach
	void tearDown() {
		playerController.deleteAll();
	}

	@Test
	void create_5_players_with_different_comunioIds_thenSuccess() {
		List<PlayerDto> players = IntStream.range(0, 18) //
				.mapToObj(i -> new PlayerDto(randomAlphabetic(6), randomAlphabetic(9))) //
				.collect(Collectors.toList());

		players.forEach(playerController::create);

		List<PlayerDto> dtosByComunioId = players.stream() //
				.map(PlayerDto::getLink) //
				.map(playerController::byLink) //
				.collect(Collectors.toList());

		assertThat(dtosByComunioId).usingElementComparatorIgnoringFields("id").containsExactlyElementsOf(players);
	}

	@Test
	void create_2_players_with_same_comunioIds__expectOnly1Player_thenSuccess() {
		PlayerDto player = new PlayerDto(randomAlphabetic(6), randomAlphabetic(9));

		playerController.create(player);
		playerController.create(player);

		assertThat(playerController.all(0, 20)).usingElementComparatorIgnoringFields("id").containsExactly(player);
	}
}
