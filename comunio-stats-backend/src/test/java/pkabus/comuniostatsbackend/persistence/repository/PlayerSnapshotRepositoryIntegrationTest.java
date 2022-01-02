package pkabus.comuniostatsbackend.persistence.repository;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
public class PlayerSnapshotRepositoryIntegrationTest {

	@Autowired
	private PlayerSnapshotRepository playerSnapshotRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	void givenPlayerSnapshot_whenFindByPlayerId_thenSuccess() {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity savedPlayer = playerRepository.save(playerEntity);

		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));
		ClubEntity savedClub = clubRepository.save(clubEntity);

		PlayerSnapshotEntity playerSnapshotEntity = new PlayerSnapshotEntity(savedPlayer, savedClub,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));

		playerSnapshotRepository.save(playerSnapshotEntity);

		Page<PlayerSnapshotEntity> retrievedPlayerSnapshots = playerSnapshotRepository
				.findByPlayerId(savedPlayer.getId(), PageRequest.of(0, 10));

		assertThat(retrievedPlayerSnapshots).containsExactly(playerSnapshotEntity);
	}
}
