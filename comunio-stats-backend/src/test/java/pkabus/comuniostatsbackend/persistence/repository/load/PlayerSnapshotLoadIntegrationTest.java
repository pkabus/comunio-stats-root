package pkabus.comuniostatsbackend.persistence.repository.load;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.FlatPlayerSnapshotService;
import pkabus.comuniostatsbackend.web.controller.ClubController;
import pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController;
import pkabus.comuniostatsbackend.web.controller.PlayerController;
import pkabus.comuniostatsbackend.web.controller.PlayerSnapshotController;
import pkabus.comuniostatsbackend.web.dto.ClubDto;
import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@SpringBootTest
public class PlayerSnapshotLoadIntegrationTest {

	private static final int LOAD_CONTROLLER = 10000;

	@Autowired
	private PlayerController playerController;

	@Autowired
	private ClubController clubController;

	@Autowired
	private PlayerSnapshotController playerSnapshotController;

	@Autowired
	private FlatPlayerSnapshotController flatPlayerSnapshotController;

	private static final int LOAD_SERVICE = 15000;

	@Autowired
	private FlatPlayerSnapshotService flatPlayerSnapshotService;

	private static final int LOAD_REPOSITORY = 100000;

	@Autowired
	private PlayerSnapshotRepository playerSnapshotRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	void repository_save_expectTotalNumberOfElements_thenAssert() {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity savedPlayer = playerRepository.save(playerEntity);

		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));
		ClubEntity savedClub = clubRepository.save(clubEntity);

		int counter = 0;
		while (counter < LOAD_REPOSITORY) {
			PlayerSnapshotEntity playerSnapshotEntity = new PlayerSnapshotEntity(savedPlayer, savedClub,
					Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));

			playerSnapshotRepository.save(playerSnapshotEntity);
			counter++;
		}

		Page<PlayerSnapshotEntity> retrievedPlayerSnapshots = playerSnapshotRepository
				.findByPlayerId(savedPlayer.getId(), PageRequest.of(0, 100));

		assertThat(retrievedPlayerSnapshots.getTotalElements()).isEqualTo(LOAD_REPOSITORY);
	}

	@Test
	void service_saveAll_expectTotalNumberOfElements_thenAssert() {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));

		int counter = LOAD_SERVICE;
		ArrayList<PlayerSnapshotEntity> entities = new ArrayList<>();
		while (counter > 0) {
			PlayerSnapshotEntity playerSnapshotEntity0 = new PlayerSnapshotEntity(playerEntity, clubEntity,
					Long.valueOf(160000), Integer.valueOf(0), LocalDate.now().minusDays(counter), randomAlphabetic(6));
			entities.add(playerSnapshotEntity0);
			counter--;
		}

		assertThat(entities).hasSize(LOAD_SERVICE);

		long before = playerSnapshotRepository.count();
		flatPlayerSnapshotService.saveAll(entities.stream());
		long after = playerSnapshotRepository.count();

		assertThat(after - before).isEqualTo(LOAD_SERVICE);
	}

	@Test
	void controller_add_expectTotalNumberOfElements_thenAssert() {
		ClubDto clubDto = new ClubDto(randomAlphabetic(6));
		clubController.create(clubDto);
		ClubDto savedClubDto = clubController.byName(clubDto.getName());

		String playerName = randomAlphabetic(6);
		String playerLink = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(playerName, playerLink);
		playerController.create(playerDto);
		PlayerDto savedPlayerDto = playerController.byName(playerName, 0, 20).getContent().iterator().next();

		int counter = LOAD_CONTROLLER;
		while (counter > 0) {
			// create LOAD number of random flat player snapshots with unique player/created
			// permutations
			FlatPlayerSnapshotDto playerSnapshot = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
					savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
					savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(counter));

			flatPlayerSnapshotController.add(playerSnapshot);
			counter--;
		}

		// return all the snapshot dtos for the dummy player
		PagedModel<PlayerSnapshotDto> pagedPlayerSnapshots = playerSnapshotController //
				.byPlayerId(savedPlayerDto.getId(), 0, 20, null);

		// assert the total number of elements is as expected
		assertThat(pagedPlayerSnapshots.getMetadata().getTotalElements()).isEqualTo(LOAD_CONTROLLER);
	}
}
