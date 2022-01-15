package pkabus.comuniostatsbackend.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

@SpringBootTest
public class PlayerServiceIntegrationTest {

	@Autowired
	PlayerService sut;

	@Test
	void findByNameContains_expectExactlyOne_thenSuccess() {
		String randName = randomAlphabetic(16);
		PlayerEntity playerEntity0 = new PlayerEntity(randName, randomAlphabetic(6));

		// persist
		PlayerEntity playerEntity = sut.save(playerEntity0);

		// verify
		Page<PlayerEntity> byNameContains = sut.findByNameContains(randName.substring(1, 14), PageRequest.of(0, 20));
		assertThat(byNameContains.getContent()).containsExactly(playerEntity);
	}

	@Test
	void findByNameContains_caseInsensitive_expectExactlyOne_thenSuccess() {
		String randName = randomAlphabetic(16);
		PlayerEntity playerEntity0 = new PlayerEntity(randName, randomAlphabetic(6));

		// persist
		PlayerEntity playerEntity = sut.save(playerEntity0);

		// verify
		Page<PlayerEntity> byNameContains = sut.findByNameContains(randName.substring(1, 14).toLowerCase(), PageRequest.of(0, 20));
		assertThat(byNameContains.getContent()).containsExactly(playerEntity);
	}
}
