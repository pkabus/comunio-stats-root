package pkabus.comuniostatsbackend.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public class PlayerSpecification {

	public static Specification<PlayerEntity> nameContains(final String name) {
		return (playerEntity, query, builder) -> //
		builder.like( //
				builder.lower(playerEntity.get("name")), //
				"%" + name.toLowerCase() + "%");
	}
}
