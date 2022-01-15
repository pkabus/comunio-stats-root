package pkabus.comuniostatsbackend.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

@SpringBootTest
public class ClubServiceIntegrationTest {

	@Autowired
	private ClubService sut;

	@Autowired
	private FlatPlayerSnapshotService flatPlayerSnapshotService;

	@Test
	void findAllCurrent_thenSuccess() {
		List<ClubEntity> clubs = new ArrayList<>();
		for (int i = 0; i < 18; i++) {
			clubs.add(createEntities());
		}

		// sut
		List<ClubEntity> result = sut.findAllOfDate(LocalDate.now().plusDays(473));

		// verify
		assertThat(result).usingElementComparatorIgnoringFields("id")//
				.containsExactlyInAnyOrderElementsOf(clubs);
	}

	private ClubEntity createEntities() {
		PlayerEntity playerEntity0 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity0 = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity entityNow0 = new PlayerSnapshotEntity(playerEntity0, clubEntity0, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now().plusDays(473), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow0);

		return clubEntity0;
	}

	@Test
	void findByNameContains_expectExactlyOne_thenSuccess() {
		String randName = randomAlphabetic(16);
		ClubEntity playerEntity0 = new ClubEntity(randName);

		// persist
		ClubEntity playerEntity = sut.save(playerEntity0);

		// verify
		Page<ClubEntity> byNameContains = sut.findByNameContains(randName.substring(1, 14), PageRequest.of(0, 20));
		assertThat(byNameContains.getContent()).containsExactly(playerEntity);
	}

	@Test
	void findByNameContains_caseInsensitive_expectExactlyOne_thenSuccess() {
		String randName = randomAlphabetic(16);
		ClubEntity playerEntity0 = new ClubEntity(randName);

		// persist
		ClubEntity playerEntity = sut.save(playerEntity0);

		// verify
		Page<ClubEntity> byNameContains = sut.findByNameContains(randName.substring(1, 14).toLowerCase(), PageRequest.of(0, 20));
		assertThat(byNameContains.getContent()).containsExactly(playerEntity);
	}
}
