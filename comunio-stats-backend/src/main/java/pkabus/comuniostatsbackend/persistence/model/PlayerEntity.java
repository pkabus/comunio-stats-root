package pkabus.comuniostatsbackend.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "sequence-generator", //
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", //
			parameters = { //
					@Parameter(name = "sequence_name", value = "player_sequence"), //
					@Parameter(name = "initial_value", value = "0"), //
					@Parameter(name = "increment_size", value = "1") } //
	)
	private Long id;

	private String name;

	private String link;

	public PlayerEntity() {
		//
	}

	public PlayerEntity(final String name, final String link) {
		super();
		this.name = name;
		this.link = link;
	}

	public PlayerEntity(final PlayerEntity playerEntity) {
		this(playerEntity.name, playerEntity.link);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlayerEntity other = (PlayerEntity) obj;
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PlayerEntity [id=" + id + ", name=" + name + ", link=" + link + "]";
	}

}
