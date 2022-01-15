package pkabus.comuniostatsbackend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;

@Component
@RequiredArgsConstructor
public class PlayerConverter {

	private final ModelMapper modelMapper;

	public PlayerDto toDto(final PlayerEntity playerEntity) {
		return modelMapper.map(playerEntity, PlayerDto.class);
	}

	public PlayerEntity toEntity(final PlayerDto playerDto) {
		return modelMapper.map(playerDto, PlayerEntity.class);
	}
}
