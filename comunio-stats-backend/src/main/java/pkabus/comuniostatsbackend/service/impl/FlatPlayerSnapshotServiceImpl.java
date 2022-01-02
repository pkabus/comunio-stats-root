package pkabus.comuniostatsbackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.FlatPlayerSnapshotService;

@Service
public class FlatPlayerSnapshotServiceImpl implements FlatPlayerSnapshotService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PlayerSnapshotRepository playerSnapshotRepo;

	private final PlayerRepository playerRepository;

	private final ClubRepository clubRepository;

	public FlatPlayerSnapshotServiceImpl(final PlayerSnapshotRepository playerSnapshotRepository,
			final PlayerRepository playerRepository, final ClubRepository clubRepository) {
		this.playerSnapshotRepo = playerSnapshotRepository;
		this.playerRepository = playerRepository;
		this.clubRepository = clubRepository;
	}

	private PlayerSnapshotEntity save(final PlayerSnapshotEntity playerSnapshot) {
		log.info("Save snapshot for " + playerSnapshot.getPlayer());
		return playerSnapshotRepo.save(playerSnapshot);
	}

	@Override
	public PlayerSnapshotEntity getOrSave(final PlayerSnapshotEntity playerSnapshot) {
		Optional<PlayerSnapshotEntity> existingSnapshot = playerSnapshotRepo
				.findByPlayerLinkAndCreated(playerSnapshot.getPlayer().getLink(), playerSnapshot.getCreated());

		// log if playerSnapshot already exists
		existingSnapshot.ifPresent(snapshot -> {
			PlayerEntity player = snapshot.getPlayer();
			log.warn("Snapshot for player with name " //
					+ player.getName() + " and link " //
					+ player.getLink() + " for date " //
					+ playerSnapshot.getCreated() //
					+ " already exists.");
		});

		// get existing snapshot or add refs (club, player) and save snapshot
		return existingSnapshot.orElseGet(() -> {
			addReferences(playerSnapshot);
			return save(playerSnapshot);
		});
	}

	@Override
	public List<PlayerSnapshotEntity> saveAll(final Stream<PlayerSnapshotEntity> stream) {
		return stream //
				.map(this::getOrSave) //
				.collect(Collectors.toList());
	}

	private PlayerSnapshotEntity addReferences(final PlayerSnapshotEntity playerSnapshotEntity) {
		PlayerEntity playerEntity = playerSnapshotEntity.getPlayer();
		PlayerEntity savedPlayerEntity = playerRepository.findByLink(playerEntity.getLink()).orElseGet(
				() -> playerRepository.save(new PlayerEntity(playerEntity.getName(), playerEntity.getLink())));

		ClubEntity clubEntity = playerSnapshotEntity.getClub();
		ClubEntity savedClubEntity = clubRepository.findByName(clubEntity.getName())
				.orElseGet(() -> clubRepository.save(new ClubEntity(clubEntity.getName())));

		playerSnapshotEntity.setClub(savedClubEntity);
		playerSnapshotEntity.setPlayer(savedPlayerEntity);
		return playerSnapshotEntity;
	}

}
