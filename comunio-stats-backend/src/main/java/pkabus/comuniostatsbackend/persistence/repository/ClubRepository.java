package pkabus.comuniostatsbackend.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

	@Query("select c from ClubEntity c where c.name like %?1%")
	List<ClubEntity> findByNameMatches(final String name);

	Optional<ClubEntity> findByName(String name);
	
}
