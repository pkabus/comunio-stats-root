package pkabus.comuniostatsbackend.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

@SpringBootTest
public class FlatPlayerSnapshotServiceIntegrationTest {

	@Autowired
	private ClubService clubService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private PlayerSnapshotService playerSnapshotService;

	@Autowired
	private FlatPlayerSnapshotService flatPlayerSnapshotService;

	@Test
	void getOrSave_thenReturnPlayerSnapshot() {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity playerSnapshotEntity = new PlayerSnapshotEntity(playerEntity, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(playerSnapshotEntity);

		Optional<PlayerEntity> savedPlayer = playerService.findByLink(playerEntity.getLink());
		Optional<ClubEntity> savedClub = clubService.findByName(clubEntity.getName());

		SoftAssertions softAssert = new SoftAssertions();
		// player and clubs should be automatically saved using the flat player snapshot
		// service
		softAssert.assertThat(savedPlayer).isPresent();
		softAssert.assertThat(savedPlayer.get()).usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(playerEntity);
		softAssert.assertThat(savedClub).isPresent();
		softAssert.assertThat(savedClub.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(clubEntity);

		// verify player snapshot exists!
		Page<PlayerSnapshotEntity> playerSnapshots = playerSnapshotService.findByPlayerId(savedPlayer.get().getId(),
				PageRequest.of(0, 10));
		softAssert.assertThat(playerSnapshots).usingElementComparatorIgnoringFields("id")
				.containsExactly(playerSnapshotEntity);

		softAssert.assertAll();
	}

	@Test
	void getOrSaveTwice_thenReturnPlayerSnapshot() {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity playerSnapshotEntity0 = new PlayerSnapshotEntity(playerEntity, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(playerSnapshotEntity0);
		PlayerSnapshotEntity playerSnapshotEntity1 = new PlayerSnapshotEntity(playerEntity, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(playerSnapshotEntity1);

		Optional<PlayerEntity> savedPlayer = playerService.findByLink(playerEntity.getLink());
		Optional<ClubEntity> savedClub = clubService.findByName(clubEntity.getName());

		SoftAssertions softAssert = new SoftAssertions();
		// player and clubs should be automatically saved using the flat player snapshot
		// service
		softAssert.assertThat(savedPlayer).isPresent();
		softAssert.assertThat(savedPlayer.get()).usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(playerEntity);
		softAssert.assertThat(savedClub).isPresent();
		softAssert.assertThat(savedClub.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(clubEntity);

		// verify player snapshot exists only once!
		Page<PlayerSnapshotEntity> playerSnapshots = playerSnapshotService.findByPlayerId(savedPlayer.get().getId(),
				PageRequest.of(0, 10));
		softAssert.assertThat(playerSnapshots).usingElementComparatorIgnoringFields("id")
				.containsExactly(playerSnapshotEntity0);

		softAssert.assertAll();
	}

}
