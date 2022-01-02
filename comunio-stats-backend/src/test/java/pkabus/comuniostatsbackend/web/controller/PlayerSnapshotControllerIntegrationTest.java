package pkabus.comuniostatsbackend.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedModel;

import pkabus.comuniostatsbackend.web.dto.ClubDto;
import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@SpringBootTest
public class PlayerSnapshotControllerIntegrationTest {

	@Autowired
	private PlayerController playerController;

	@Autowired
	private ClubController clubController;

	@Autowired
	private PlayerSnapshotController playerSnapshotController;

	@Autowired
	private FlatPlayerSnapshotController flatPlayerSnapshotController;

	@Test
	void givenPlayerSnapshots_byPlayerId_assertDefaultOrder_thenSuccess() {
		ClubDto clubDto = new ClubDto(randomAlphabetic(6));
		clubController.create(clubDto);
		ClubDto savedClubDto = clubController.byName(clubDto.getName());

		String playerName = randomAlphabetic(6);
		String playerLink = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(playerName, playerLink);
		playerController.create(playerDto);
		PlayerDto savedPlayerDto = playerController.byName(playerName, 0, 20).getContent().iterator().next();

		// create six snapshots for the same player
		FlatPlayerSnapshotDto playerSnapshotFive = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
				savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(5));

		FlatPlayerSnapshotDto playerSnapshotFour = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
				savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(4));

		FlatPlayerSnapshotDto playerSnapshotThree = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
				savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(3));

		FlatPlayerSnapshotDto playerSnapshotTwo = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
				savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(2));

		FlatPlayerSnapshotDto playerSnapshotOne = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
				savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(1));

		FlatPlayerSnapshotDto playerSnapshotZero = new FlatPlayerSnapshotDto(savedPlayerDto.getName(),
				savedPlayerDto.getLink(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now());

		// add unordered
		flatPlayerSnapshotController.add(playerSnapshotThree);
		flatPlayerSnapshotController.add(playerSnapshotOne);
		flatPlayerSnapshotController.add(playerSnapshotFive);
		flatPlayerSnapshotController.add(playerSnapshotTwo);
		flatPlayerSnapshotController.add(playerSnapshotZero);
		flatPlayerSnapshotController.add(playerSnapshotFour);

		// only return the most recent five snapshots
		PagedModel<PlayerSnapshotDto> pagedPlayerSnapshots = playerSnapshotController //
				.byPlayerIdAndCreatedBetween(savedPlayerDto.getId(), //
						LocalDate.now().minusDays(4), //
						LocalDate.now(), 0, 5, null);

		// assert the order starting at the most recent entry (desc)
		assertThat(pagedPlayerSnapshots.getContent()).map(PlayerSnapshotDto::getCreated) //
				.isEqualTo(List.of( //
						playerSnapshotFour.getCreated(), //
						playerSnapshotThree.getCreated(), //
						playerSnapshotTwo.getCreated(), //
						playerSnapshotOne.getCreated(), //
						playerSnapshotZero.getCreated()));
	}

}
