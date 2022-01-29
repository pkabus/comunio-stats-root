package pkabus.comuniostatsbackend.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>, JpaSpecificationExecutor<PlayerEntity> {

	Page<PlayerEntity> findByName(String name, Pageable page);

	Optional<PlayerEntity> findByLink(String link);

}
