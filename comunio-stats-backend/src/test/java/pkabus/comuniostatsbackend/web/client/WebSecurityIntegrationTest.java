package pkabus.comuniostatsbackend.web.client;

import static org.assertj.core.api.Assertions.assertThat;
import static pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController.BASE_FLAT_SNAPSHOTS;
import static pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController.CREATE;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebSecurityIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void givenAuthRequestOnPrivateService_then201Created() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File testJsonFile = new File(classLoader.getResource("single_bundesliga_player.json").getFile());
		List<FlatPlayerSnapshotDto> flatPlayers = Arrays
				.asList(objectMapper.readValue(testJsonFile, FlatPlayerSnapshotDto[].class));

		ResponseEntity<Void> postResponse = restTemplate.withBasicAuth("crawler", "password") //
				.postForEntity(BASE_FLAT_SNAPSHOTS + CREATE, flatPlayers, Void.class);

		assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
}
