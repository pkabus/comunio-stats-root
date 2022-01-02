package pkabus.comuniostatsbackend.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;

public class ObjectMapperTest {

	private final static FlatPlayerSnapshotDto EXPECTED_OBJECT_0 = new FlatPlayerSnapshotDto("Neuer",
			"/csprofile/30755-Neuer", "Keeper", 30, "FC Bayern München", 2170000L, LocalDate.of(2021, 1, 8));
	private final static FlatPlayerSnapshotDto EXPECTED_OBJECT_1 = new FlatPlayerSnapshotDto("R. Hoffmann",
			"/csprofile/32855-R.+Hoffmann", "Keeper", 0, "FC Bayern München", 160000L,
			LocalDate.of(2021, 1, 8));

	private final static String TEST_JSON_BRACKETS_EVERYWHERE = "[\n"
			+ "{\"name\": [\"Neuer\"], \"link\": [\"/csprofile/30755-Neuer\"], \"position\": [\"Keeper\"], \"points_during_current_season\": [\"30\"], \"club\": [\"FC Bayern München\"], \"market_value\": [\"2170000\"], \"created\": \"2021-01-08 15:52:22\"}]";

	private final static String TEST_JSON_BRACKETS_AT_STRING_VALUES = "{\"name\": [\"Neuer\"], \"link\": [\"/csprofile/30755-Neuer\"], \"position\": [\"Keeper\"], \"points_during_current_season\": [\"30\"], \"club\": [\"FC Bayern München\"], \"market_value\": [\"2170000\"], \"created\": \"2021-01-08 15:52:22\"}";

	private final static String TEST_JSON_NO_BRACKETS = "{\"name\": \"Neuer\", \"link\": \"/csprofile/30755-Neuer\", \"position\": \"Keeper\", \"points_during_current_season\": \"30\", \"club\": \"FC Bayern München\", \"market_value\": \"2170000\", \"created\": \"2021-01-08 15:52:22\"}";

	private final static String TEST_JSON_BRACKETS_EVERYWHERE_TWO_ENTRIES = "[\n"
			+ "{\"name\": [\"Neuer\"], \"link\": [\"/csprofile/30755-Neuer\"], \"position\": [\"Keeper\"], \"points_during_current_season\": [\"30\"], \"club\": [\"FC Bayern München\"], \"market_value\": [\"2170000\"], \"created\": \"2021-01-08 15:52:22\"},\n"
			+ "{\"name\": [\"R. Hoffmann\"], \"link\": [\"/csprofile/32855-R.+Hoffmann\"], \"position\": [\"Keeper\"], \"points_during_current_season\": [\"0\"], \"club\": [\"FC Bayern München\"], \"market_value\": [\"160000\"], \"created\": \"2021-01-08 15:52:22\"}\n"
			+ "]";

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		this.objectMapper = new ObjectMapper();

		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);

		JavaTimeModule timeModule = new JavaTimeModule();
		objectMapper.registerModule(timeModule);
		LocalDateDeserializer localDateTimeDeserializer = new LocalDateDeserializer(
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		timeModule.addDeserializer(LocalDate.class, localDateTimeDeserializer);
	}

	@Test
	void whenJson_without_extra_brackets_thenSuccess() throws JsonMappingException, JsonProcessingException {
		List<FlatPlayerSnapshotDto> flatPlayers = Arrays
				.asList(objectMapper.readValue(TEST_JSON_NO_BRACKETS, FlatPlayerSnapshotDto[].class));

		assertThat(flatPlayers).hasSize(1);
		assertThat(flatPlayers).containsExactly(EXPECTED_OBJECT_0);
	}

	@Test
	void whenJson_with_extra_brackets_at_string_values_thenSuccess()
			throws JsonMappingException, JsonProcessingException {
		List<FlatPlayerSnapshotDto> flatPlayers = Arrays
				.asList(objectMapper.readValue(TEST_JSON_BRACKETS_AT_STRING_VALUES, FlatPlayerSnapshotDto[].class));

		assertThat(flatPlayers).hasSize(1);
		assertThat(flatPlayers).containsExactly(EXPECTED_OBJECT_0);
	}

	@Test
	void whenJson_with_brackets_everywhere_thenSuccess() throws JsonMappingException, JsonProcessingException {
		List<FlatPlayerSnapshotDto> flatPlayers = Arrays
				.asList(objectMapper.readValue(TEST_JSON_BRACKETS_EVERYWHERE, FlatPlayerSnapshotDto[].class));

		assertThat(flatPlayers).hasSize(1);
		assertThat(flatPlayers).containsExactly(EXPECTED_OBJECT_0);
	}

	@Test
	void whenJson_with_brackets_everywhere_two_entries_thenSuccess()
			throws JsonMappingException, JsonProcessingException {
		List<FlatPlayerSnapshotDto> flatPlayers = Arrays.asList(
				objectMapper.readValue(TEST_JSON_BRACKETS_EVERYWHERE_TWO_ENTRIES, FlatPlayerSnapshotDto[].class));

		assertThat(flatPlayers).hasSize(2);
		assertThat(flatPlayers).containsExactlyInAnyOrder(EXPECTED_OBJECT_0, EXPECTED_OBJECT_1);
	}
}
