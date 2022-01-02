package pkabus.comuniostatsbackend.persistence.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerSnapshotEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "sequence-generator", //
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", //
			parameters = { //
					@Parameter(name = "sequence_name", value = "player_snapshot_sequence"), //
					@Parameter(name = "initial_value", value = "0"), //
					@Parameter(name = "increment_size", value = "1") } //
	)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private PlayerEntity player;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ClubEntity club;

	private Long marketValue;

	private Integer pointsDuringCurrentSeason;

	private LocalDate created;

	private String position;

	public PlayerSnapshotEntity() {
		super();
	}

	public PlayerSnapshotEntity(final PlayerEntity playerEntity, final ClubEntity clubEntity, final Long marketValue,
			final Integer pointsDuringCurrentSeason, final LocalDate created, final String position) {
		super();
		this.player = playerEntity;
		this.club = clubEntity;
		this.marketValue = marketValue;
		this.pointsDuringCurrentSeason = pointsDuringCurrentSeason;
		this.created = created;
		this.position = position;
	}

	public PlayerSnapshotEntity(final Long id, final PlayerEntity playerEntity, final ClubEntity clubEntity,
			final Long marketValue, final Integer pointsDuringCurrentSeason, final LocalDate created,
			final String position) {
		super();
		this.id = id;
		this.player = playerEntity;
		this.club = clubEntity;
		this.marketValue = marketValue;
		this.pointsDuringCurrentSeason = pointsDuringCurrentSeason;
		this.created = created;
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((marketValue == null) ? 0 : marketValue.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((pointsDuringCurrentSeason == null) ? 0 : pointsDuringCurrentSeason.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		PlayerSnapshotEntity other = (PlayerSnapshotEntity) obj;
		if (club == null) {
			if (other.club != null) {
				return false;
			}
		} else if (!club.equals(other.club)) {
			return false;
		}
		if (created == null) {
			if (other.created != null) {
				return false;
			}
		} else if (!created.equals(other.created)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (marketValue == null) {
			if (other.marketValue != null) {
				return false;
			}
		} else if (!marketValue.equals(other.marketValue)) {
			return false;
		}
		if (player == null) {
			if (other.player != null) {
				return false;
			}
		} else if (!player.equals(other.player)) {
			return false;
		}
		if (pointsDuringCurrentSeason == null) {
			if (other.pointsDuringCurrentSeason != null) {
				return false;
			}
		} else if (!pointsDuringCurrentSeason.equals(other.pointsDuringCurrentSeason)) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PlayerSnapshotEntity [id=" + id + ", player=" + player + ", club=" + club + ", marketValue="
				+ marketValue + ", pointsDuringCurrentSeason=" + pointsDuringCurrentSeason + ", created=" + created
				+ ", position=" + position + "]";
	}

	public void setClub(final ClubEntity clubEntity) {
		this.club = clubEntity;
	}

	public void setPlayer(final PlayerEntity playerEntity) {
		this.player = playerEntity;
	}

}
