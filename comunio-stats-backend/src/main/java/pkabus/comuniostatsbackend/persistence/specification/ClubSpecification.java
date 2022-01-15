package pkabus.comuniostatsbackend.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public class ClubSpecification {

	public static Specification<ClubEntity> nameContains(final String name) {
		return (clubEntity, query, builder) -> //
		builder.like( //
				builder.lower(clubEntity.get("name")), //
				"%" + name.toLowerCase() + "%");
	}
}
