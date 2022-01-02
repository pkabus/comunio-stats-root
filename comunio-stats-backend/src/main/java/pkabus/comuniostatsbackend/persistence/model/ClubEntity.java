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
public class ClubEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "sequence-generator", //
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", //
			parameters = { //
					@Parameter(name = "sequence_name", value = "club_sequence"), //
					@Parameter(name = "initial_value", value = "0"), //
					@Parameter(name = "increment_size", value = "1") } //
	)
	private Long id;

	private String name;

	public ClubEntity() {
		super();
	}

	public ClubEntity(final String name) {
		super();
		this.name = name;
	}

	public ClubEntity(final Long id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ClubEntity other = (ClubEntity) obj;
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
		return "ClubEntity [id=" + id + ", name=" + name + "]";
	}
}
