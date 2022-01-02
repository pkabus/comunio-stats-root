package pkabus.comuniostatsbackend.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;

@Service
public class PlayerSnapshotServiceImpl implements PlayerSnapshotService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PlayerSnapshotRepository playerSnapshotRepo;

	public PlayerSnapshotServiceImpl(final PlayerSnapshotRepository playerSnapshotRepository) {
		this.playerSnapshotRepo = playerSnapshotRepository;
	}

	@Override
	public Optional<PlayerSnapshotEntity> findById(final Long id) {
		return playerSnapshotRepo.findById(id);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByPlayerId(final Long id, final Pageable pageable) {
		return playerSnapshotRepo.findByPlayerId(id, pageable);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByPlayerIdAndCreatedBetween(final Long id, final LocalDate start,
			final LocalDate end, final Pageable pageable) {
		return playerSnapshotRepo.findByPlayerIdAndCreatedBetween(id, start, end, pageable);
	}

	@Override
	public void deleteById(final Long id) {
		Optional<PlayerSnapshotEntity> byId = playerSnapshotRepo.findById(id);

		if (byId.isEmpty()) {
			log.warn("No player snapshot found with id: " + id);
			return;
		}
			playerSnapshotRepo.delete(byId.get());
			log.info("Deleted player snapshot with id: " + id);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByClubNameAndCreated(final String name, final LocalDate date,
			final Pageable page) {
		return playerSnapshotRepo.findByClubNameAndCreated(name, date, page);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByClubIdAndCreated(final Long id, final LocalDate date, final Pageable page) {
		return playerSnapshotRepo.findByClubIdAndCreated(id, date, page);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByClubId(final Long id, final boolean mostRecentOnly, final Pageable page) {
		if (mostRecentOnly) {
			Page<PlayerSnapshotEntity> findMostRecent100 = playerSnapshotRepo.findByClubId(id,
					PageRequest.of(0, 100, Sort.by(Order.desc("created"))));
			if (findMostRecent100.getTotalElements() > 0 //
					&& findMostRecent100.getContent().get(0).getCreated() != null) {
				LocalDate wantedDate = findMostRecent100.getContent().get(0).getCreated();
				return this.findByClubIdAndCreated(id, wantedDate, page);
			}
		}

		// if not only most recent entities, simply use default repo functionality
		return playerSnapshotRepo.findByClubId(id, page);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByClubName(final String name, final boolean mostRecentOnly,
			final Pageable page) {
		if (mostRecentOnly) {
			Page<PlayerSnapshotEntity> findMostRecent100 = playerSnapshotRepo.findByClubName(name,
					PageRequest.of(0, 100, Sort.by(Order.desc("created"))));
			if (findMostRecent100.getTotalElements() > 0 //
					&& findMostRecent100.getContent().get(0).getCreated() != null) {
				LocalDate wantedDate = findMostRecent100.getContent().get(0).getCreated();
				return this.findByClubNameAndCreated(name, wantedDate, page);
			}
		}

		// if not only most recent entities, simply use default repo functionality
		return playerSnapshotRepo.findByClubName(name, page);
	}

	@Override
	public void deleteBeforeDate(final LocalDate date) {
		List<PlayerSnapshotEntity> playerSnapshots = playerSnapshotRepo.findByCreatedLessThan(date);
		playerSnapshotRepo.deleteAll(playerSnapshots);
		log.info("Deleted player snapshots before " + date);
	}

}
