package pkabus.comuniostatsbackend.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {

	private Long id;

	private String name;

	private String link;

	public PlayerDto() {
		//
	}

	public PlayerDto(final String name, final String link) {
		super();
		this.name = name;
		this.link = link;
	}

	public PlayerDto(final PlayerDto player) {
		this(player.name, player.link);
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
		PlayerDto other = (PlayerDto) obj;
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
		return "PlayerDto [id=" + id + ", name=" + name + ", link=" + link + "]";
	}

}
