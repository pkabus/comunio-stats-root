package pkabus.comuniostatsbackend.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.ClubService;

@Service
public class ClubServiceImpl implements ClubService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ClubRepository clubRepo;

	private final PlayerSnapshotRepository playerSnapshotRepo;

	public ClubServiceImpl(final ClubRepository clubRepo, final PlayerSnapshotRepository playerSnapshotRepo) {
		this.clubRepo = clubRepo;
		this.playerSnapshotRepo = playerSnapshotRepo;
	}

	@Override
	public Optional<ClubEntity> findById(final Long id) {
		return clubRepo.findById(id);
	}

	@Override
	public List<ClubEntity> findAllOfDate(final LocalDate date) {
		return findAllCurrentRecursive(date, 0);
	}

	private List<ClubEntity> findAllCurrentRecursive(final LocalDate date, final int iteration) {
		if (iteration > 10) {
			logger.warn("Did not find any club for the last " + iteration + " days!");
			return Collections.emptyList();
		}

		Page<PlayerSnapshotEntity> snapshotsByDate = playerSnapshotRepo.findByCreated(date,
				PageRequest.of(0, 550, Sort.by(Order.desc("created"))));
		List<ClubEntity> clubList = snapshotsByDate.map(snapshot -> snapshot.getClub()) //
				.stream() //
				.distinct() //
				.collect(Collectors.toList());

		if (clubList.size() > 17) {
			// if there are at least 18 clubs, return the first 18
			return clubList.subList(0, 18);
		}

		return findAllCurrentRecursive(date.minusDays(1), iteration + 1);
	}

	@Override
	public Page<ClubEntity> findAll(final Pageable page) {
		return clubRepo.findAll(page);
	}

	@Override
	public ClubEntity save(final ClubEntity club) {
		Optional<ClubEntity> clubByName = clubRepo.findByName(club.getName());

		if (clubByName.isPresent()) {
			logger.warn(String.format("Club '%s' is already present. Not created again.", clubByName.get().getName()));
		}

		return clubByName.orElseGet(() -> clubRepo.save(club));
	}

	@Override
	public Optional<ClubEntity> findByName(final String name) {
		return clubRepo.findByName(name);
	}

	@Override
	public void deleteAll() {
		clubRepo.deleteAll();
	}

	@Override
	public void delete(final ClubEntity entity) {
		clubRepo.delete(entity);
	}

	@Override
	public void deleteByName(final String name) {
		clubRepo.findByName(name).ifPresentOrElse(clubRepo::delete,
				() -> logger.info(String.format("Club '%s' not found. Cannot be deleted.", name)));
	}

}
