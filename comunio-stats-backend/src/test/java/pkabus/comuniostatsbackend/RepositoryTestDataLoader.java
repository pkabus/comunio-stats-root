package pkabus.comuniostatsbackend;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;

@Component
public class RepositoryTestDataLoader implements ApplicationContextAware {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private PlayerSnapshotRepository playerSnapshotRepository;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		PlayerEntity savedPlayer = playerRepository.save(playerEntity);

		ClubEntity clubEntity = new ClubEntity(new Random().nextLong(), randomAlphabetic(6));
		ClubEntity savedClub = clubRepository.save(clubEntity);

		playerSnapshotRepository.save(new PlayerSnapshotEntity(new Random().nextLong(), savedPlayer, savedClub,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6)));

		playerSnapshotRepository.save(new PlayerSnapshotEntity(new Random().nextLong(), savedPlayer, savedClub,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6)));

		playerSnapshotRepository.save(new PlayerSnapshotEntity(new Random().nextLong(), savedPlayer, savedClub,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6)));

		playerSnapshotRepository.save(new PlayerSnapshotEntity(new Random().nextLong(), savedPlayer, savedClub,
				Long.valueOf(160000), Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6)));
	}
}
