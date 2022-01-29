package pkabus.comuniostatsbackend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.web.dto.ClubDto;

@Component
@RequiredArgsConstructor
public class ClubConverter {

	private final ModelMapper modelMapper;

	public ClubDto toDto(final ClubEntity clubEntity) {
		return modelMapper.map(clubEntity, ClubDto.class);
	}

	public ClubEntity toEntity(final ClubDto clubDto) {
		return modelMapper.map(clubDto, ClubEntity.class);
	}
}
