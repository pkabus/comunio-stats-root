package pkabus.comuniostatsbackend.web.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubSnapshotDto {

	private Long id;

	private LocalDate created;

	private ClubDto club;

	public ClubSnapshotDto() {
		super();
	}

	public ClubSnapshotDto(final LocalDate created, final ClubDto club) {
		super();
		this.created = created;
		this.club = club;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ClubSnapshotDto other = (ClubSnapshotDto) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "ClubSnapshotDto [id=" + id + ", created=" + created + ", club=" + club + "]";
	}
}
