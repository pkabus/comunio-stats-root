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

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

@SpringBootTest
public class ClubServiceIntegrationTest {

	@Autowired
	private ClubService clubService;

	@Autowired
	private FlatPlayerSnapshotService flatPlayerSnapshotService;

	@Test
	void findAllCurrent_thenSuccess() {
		List<ClubEntity> clubs = new ArrayList<>();
		for (int i = 0; i < 18; i++) {
			clubs.add(createEntities());
		}

		// sut
		List<ClubEntity> result = clubService.findAllOfDate(LocalDate.now().plusDays(473));

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
}
