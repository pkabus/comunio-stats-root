package pkabus.comuniostatsbackend.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;

@Configuration
public class PersistenceConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		// map foo_bar to fooBar
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

		// accept single values in json array
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		// single values in array will be unwrapped "key": ["value"]
		objectMapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);

		// ignore unknown properties (due to the fact that 'id' properties should be ignored)
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// add time module to ensure Strings can be deserialized to LocalDate with given pattern
		JavaTimeModule timeModule = new JavaTimeModule();
		objectMapper.registerModule(timeModule);
		LocalDateDeserializer localDateTimeDeserializer = new LocalDateDeserializer(
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		timeModule.addDeserializer(LocalDate.class, localDateTimeDeserializer);

		// to enable the PagedModel object mapping
		objectMapper.registerModule(new Jackson2HalModule());

		return objectMapper;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);

		PropertyMap<FlatPlayerSnapshotDto, PlayerEntity> flatSnapshotToPlayerMap = new PropertyMap<FlatPlayerSnapshotDto, PlayerEntity>() {
			@Override
			protected void configure() {
				map().setLink(source.getLink());
				map().setName(source.getName());
			}
		};

		PropertyMap<FlatPlayerSnapshotDto, ClubEntity> flatSnapshotToClubMap = new PropertyMap<FlatPlayerSnapshotDto, ClubEntity>() {
			@Override
			protected void configure() {
				map().setName(source.getClub());
			}
		};

		modelMapper.addMappings(flatSnapshotToPlayerMap);
		modelMapper.addMappings(flatSnapshotToClubMap);
		return modelMapper;
	}

}
