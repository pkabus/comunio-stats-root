package pkabus.comuniostatsbackend.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
public class PlayerSnapshotServiceIntegrationTest {

	@Autowired
	private ClubService clubService;

	@Autowired
	private PlayerSnapshotService playerSnapshotService;

	@Autowired
	private FlatPlayerSnapshotService flatPlayerSnapshotService;

	@Test
	void findByClubName_mostRecentOnly_thenSuccess() {
		PlayerEntity playerEntity0 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity playerEntity1 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity entityNow0 = new PlayerSnapshotEntity(playerEntity0, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow0);

		PlayerSnapshotEntity entityNow1 = new PlayerSnapshotEntity(playerEntity1, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow1);

		PlayerSnapshotEntity entityYesterday0 = new PlayerSnapshotEntity(playerEntity0, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now().minusDays(1), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityYesterday0);

		Page<PlayerSnapshotEntity> result = playerSnapshotService.findByClubName(clubEntity.getName(), true,
				PageRequest.of(0, 20));

		assertThat(result.getContent()).usingElementComparatorIgnoringFields("id")//
				.containsExactlyInAnyOrderElementsOf(List.of(entityNow0, entityNow1));
	}

	@Test
	void findByClubName_thenSuccess() {
		PlayerEntity playerEntity0 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity playerEntity1 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity entityNow0 = new PlayerSnapshotEntity(playerEntity0, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow0);

		PlayerSnapshotEntity entityNow1 = new PlayerSnapshotEntity(playerEntity1, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow1);

		PlayerSnapshotEntity entityYesterday0 = new PlayerSnapshotEntity(playerEntity0, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now().minusDays(1), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityYesterday0);

		Page<PlayerSnapshotEntity> result = playerSnapshotService.findByClubName(clubEntity.getName(), false,
				PageRequest.of(0, 20));

		assertThat(result.getContent()).usingElementComparatorIgnoringFields("id") //
				.containsExactlyInAnyOrderElementsOf(List.of(entityNow0, entityNow1, entityYesterday0));
	}

	@Test
	void findByClubId_mostRecentOnly_thenSuccess() {
		PlayerEntity playerEntity0 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity playerEntity1 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity entityNow0 = new PlayerSnapshotEntity(playerEntity0, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow0);

		PlayerSnapshotEntity entityNow1 = new PlayerSnapshotEntity(playerEntity1, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow1);

		PlayerSnapshotEntity entityYesterday0 = new PlayerSnapshotEntity(playerEntity0, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now().minusDays(1), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityYesterday0);

		Optional<ClubEntity> savedClub = clubService.findByName(clubEntity.getName());
		assertThat(savedClub).isPresent();

		Page<PlayerSnapshotEntity> result = playerSnapshotService.findByClubId(savedClub.get().getId(), true,
				PageRequest.of(0, 20));

		assertThat(result.getContent()).usingElementComparatorIgnoringFields("id") //
				.containsExactlyInAnyOrderElementsOf(List.of(entityNow0, entityNow1));
	}

	@Test
	void findByClubId_thenSuccess() {
		PlayerEntity playerEntity0 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity playerEntity1 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity entityNow0 = new PlayerSnapshotEntity(playerEntity0, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow0);

		PlayerSnapshotEntity entityNow1 = new PlayerSnapshotEntity(playerEntity1, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow1);

		PlayerSnapshotEntity entityYesterday0 = new PlayerSnapshotEntity(playerEntity0, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now().minusDays(1), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityYesterday0);

		Optional<ClubEntity> savedClub = clubService.findByName(clubEntity.getName());
		assertThat(savedClub).isPresent();

		Page<PlayerSnapshotEntity> result = playerSnapshotService.findByClubId(savedClub.get().getId(), false,
				PageRequest.of(0, 20));

		assertThat(result.getContent()).usingElementComparatorIgnoringFields("id") //
				.containsExactlyInAnyOrderElementsOf(List.of(entityNow0, entityNow1, entityYesterday0));
	}


	@Test
	void deleteBeforeDate_thenSuccess() {
		PlayerEntity playerEntity0 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity playerEntity1 = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		PlayerSnapshotEntity entityNow0 = new PlayerSnapshotEntity(playerEntity0, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityNow0);

		PlayerSnapshotEntity entityYesterday0 = new PlayerSnapshotEntity(playerEntity1, clubEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now().minusDays(1), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityYesterday0);

		PlayerSnapshotEntity entityYesterday1 = new PlayerSnapshotEntity(playerEntity0, clubEntity,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now().minusDays(1), randomAlphabetic(6));
		flatPlayerSnapshotService.getOrSave(entityYesterday1);

		Optional<ClubEntity> savedClub = clubService.findByName(clubEntity.getName());
		assertThat(savedClub).isPresent();

		playerSnapshotService.deleteBeforeDate(LocalDate.now());

		Page<PlayerSnapshotEntity> result = playerSnapshotService.findByClubId(savedClub.get().getId(), false,
				PageRequest.of(0, 20));

		assertThat(result.getContent()).usingElementComparatorIgnoringFields("id") //
				.containsExactly(entityNow0);
	}
}
