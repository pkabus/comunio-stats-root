package pkabus.comuniostatsbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public interface PlayerService {

	Optional<PlayerEntity> findById(Long id);

	PlayerEntity save(PlayerEntity club);

	Page<PlayerEntity> findByName(String name, Pageable page);

	Optional<PlayerEntity> findByLink(String link);

	Page<PlayerEntity> findAll(Pageable page);

	void delete(PlayerEntity player);

	void deleteAll();

	void deleteByLink(String link);
}
