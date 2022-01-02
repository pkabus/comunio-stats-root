package pkabus.comuniostatsbackend.web.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerSnapshotDto {

	private Long id;

	private PlayerDto player;

	private ClubDto club;

	private Long marketValue;

	private Integer pointsDuringCurrentSeason;

	private LocalDate created;

	private String position;

	public PlayerSnapshotDto() {
		super();
	}

	public PlayerSnapshotDto(final PlayerDto player, final ClubDto club, final Long marketValue,
			final Integer pointsDuringCurrentSeason, final LocalDate created, final String position) {
		super();
		this.player = player;
		this.club = club;
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
		PlayerSnapshotDto other = (PlayerSnapshotDto) obj;
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
		return "PlayerSnapshotDto [id=" + id + ", player=" + player + ", club=" + club + ", marketValue=" + marketValue
				+ ", pointsDuringCurrentSeason=" + pointsDuringCurrentSeason + ", created=" + created + ", position="
				+ position + "]";
	}

}
