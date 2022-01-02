package pkabus.comuniostatsbackend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubService {

	List<ClubEntity> findAllOfDate(LocalDate date);

	Page<ClubEntity> findAll(Pageable page);

	Optional<ClubEntity> findById(Long id);

	ClubEntity save(ClubEntity club);

	Optional<ClubEntity> findByName(String name);

	void deleteAll();

	void delete(ClubEntity entity);

	void deleteByName(String name);
}
