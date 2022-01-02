package pkabus.comuniostatsbackend.web.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlatPlayerSnapshotDto {

	private String name;

	private String link;

	private String position;

	private Integer pointsDuringCurrentSeason;

	private String club;

	private Long marketValue;

	private LocalDate created;

	public FlatPlayerSnapshotDto() {
		//
	}

	public FlatPlayerSnapshotDto(final String name, final String link, final String position,
			final Integer pointsDuringCurrentSeason, final String club, final Long marketValue,
			final LocalDate created) {
		super();
		this.name = name;
		this.link = link;
		this.position = position;
		this.pointsDuringCurrentSeason = pointsDuringCurrentSeason;
		this.club = club;
		this.marketValue = marketValue;
		this.created = created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((marketValue == null) ? 0 : marketValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FlatPlayerSnapshotDto other = (FlatPlayerSnapshotDto) obj;
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
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (marketValue == null) {
			if (other.marketValue != null) {
				return false;
			}
		} else if (!marketValue.equals(other.marketValue)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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
		return "FlatPlayerSnapshotDto [name=" + name + ", link=" + link + ", position=" + position
				+ ", pointsDuringCurrentSeason=" + pointsDuringCurrentSeason + ", club=" + club + ", marketValue="
				+ marketValue + ", created=" + created + "]";
	}

}
