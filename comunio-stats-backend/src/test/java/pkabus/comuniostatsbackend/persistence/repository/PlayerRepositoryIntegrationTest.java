package pkabus.comuniostatsbackend.persistence.repository;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

@SpringBootTest
public class PlayerRepositoryIntegrationTest {

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	void whenSavingNewPlayer_thenSuccess() {
		PlayerEntity player = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));

		assertNotNull(playerRepository.save(player));
	}

	@Test
	void givenPlayer_whenFindById_thenSuccess() {
		PlayerEntity player = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		playerRepository.save(player);

		Optional<PlayerEntity> retrievedPlayer = playerRepository.findById(player.getId());

		assertEquals(retrievedPlayer.get(), player);
	}

	@Test
	void givenPlayer_whenFindByName_thenSuccess() {
		PlayerEntity player = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		playerRepository.save(player);

		Page<PlayerEntity> retrievedPlayer = playerRepository.findByName(player.getName(), PageRequest.of(0,  20));

		assertEquals(retrievedPlayer.getContent().get(0), player);
	}

}
