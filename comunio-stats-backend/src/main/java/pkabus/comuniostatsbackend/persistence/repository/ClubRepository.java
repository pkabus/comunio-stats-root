package pkabus.comuniostatsbackend.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubRepository extends JpaRepository<ClubEntity, Long>, JpaSpecificationExecutor<ClubEntity> {

	Optional<ClubEntity> findByName(String name);

}
