package pkabus.comuniostatsbackend.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotService {

	Optional<PlayerSnapshotEntity> findById(Long id);

	Page<PlayerSnapshotEntity> findByPlayerId(Long id, Pageable pageable);

	Page<PlayerSnapshotEntity> findByPlayerIdAndCreatedBetween(Long id, LocalDate start, LocalDate end,
			Pageable pageable);

	Page<PlayerSnapshotEntity> findByClubNameAndCreated(String name, LocalDate date, Pageable page);

	Page<PlayerSnapshotEntity> findByClubName(String name, boolean mostRecentOnly, Pageable page);

	Page<PlayerSnapshotEntity> findByClubIdAndCreated(Long id, LocalDate date, Pageable page);

	Page<PlayerSnapshotEntity> findByClubId(Long id, boolean mostRecentOnly, Pageable page);

	void deleteById(Long id);

	void deleteBeforeDate(LocalDate date);

}
