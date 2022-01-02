package pkabus.comuniostatsbackend.web.controller;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@RestController
@RequestMapping(PlayerSnapshotController.BASE_PLAYERS_SNAPSHOTS)
public class PlayerSnapshotController {

	public static final String BASE_PLAYERS_SNAPSHOTS = "/players/snapshots";
	public static final String CREATE = "/create";
	public static final String DELETE = "/delete";

	private static final Sort SORT_DEFAULT = Sort.by(Order.asc("created"));

	private final PlayerSnapshotService playerSnapshotService;

	private final ModelMapper modelMapper;

	public PlayerSnapshotController(final PlayerSnapshotService playerSnapshotService, final ModelMapper modelMapper) {
		super();
		this.playerSnapshotService = playerSnapshotService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(params = { "playerId", "start", "end" })
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerSnapshotDto> byPlayerIdAndCreatedBetween(@RequestParam final Long playerId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate start,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate end,
			@RequestParam(defaultValue = "0") final Integer page, //
			@RequestParam(defaultValue = "20") final Integer size, //
			@RequestParam(required = false) final Sort sort) {
		Sort sortOrDefault = sort != null ? sort : SORT_DEFAULT;
		PageRequest pageRequest = PageRequest.of(page, size, sortOrDefault);
		Page<PlayerSnapshotDto> playerSnapshotPage = playerSnapshotService //
				.findByPlayerIdAndCreatedBetween(playerId, start, end, pageRequest) //
				.map(this::snapshotToDto);

		return toPagedModel(playerSnapshotPage);

	}

	@GetMapping(params = "playerId")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerSnapshotDto> byPlayerId(@RequestParam final Long playerId,
			@RequestParam(defaultValue = "0") final Integer page, //
			@RequestParam(defaultValue = "20") final Integer size, //
			@RequestParam(required = false) final Sort sort) {
		Sort sortOrDefault = sort != null ? sort : SORT_DEFAULT;
		PageRequest pageRequest = PageRequest.of(page, size, sortOrDefault);
		Page<PlayerSnapshotDto> playerSnapshotPage = playerSnapshotService //
				.findByPlayerId(playerId, pageRequest) //
				.map(this::snapshotToDto);

		return toPagedModel(playerSnapshotPage);
	}

	@GetMapping(params = { "clubId", "date" })
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerSnapshotDto> byClubIdAndDate(@RequestParam final Long clubId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate date,
			@RequestParam(defaultValue = "0") final Integer page, //
			@RequestParam(defaultValue = "20") final Integer size, //
			@RequestParam(required = false) final Sort sort) {
		Sort sortOrDefault = sort != null ? sort : SORT_DEFAULT;
		PageRequest pageRequest = PageRequest.of(page, size, sortOrDefault);
		Page<PlayerSnapshotDto> playerSnapshotPage = playerSnapshotService //
				.findByClubIdAndCreated(clubId, date, pageRequest) //
				.map(this::snapshotToDto);

		return toPagedModel(playerSnapshotPage);
	}

	@GetMapping(params = "clubId")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerSnapshotDto> byClubId(@RequestParam final Long clubId,
			@RequestParam(defaultValue = "false") final boolean mostRecentOnly,
			@RequestParam(defaultValue = "0") final Integer page, //
			@RequestParam(defaultValue = "20") final Integer size, //
			@RequestParam(required = false) final Sort sort) {
		Sort sortOrDefault = sort != null ? sort : SORT_DEFAULT;
		PageRequest pageRequest = PageRequest.of(page, size, sortOrDefault);
		Page<PlayerSnapshotDto> playerSnapshotPage = playerSnapshotService //
				.findByClubId(clubId, mostRecentOnly, pageRequest).map(this::snapshotToDto);

		return toPagedModel(playerSnapshotPage);
	}

	@GetMapping(params = "clubName")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerSnapshotDto> byClubName(@RequestParam final String clubName,
			@RequestParam(defaultValue = "false") final boolean mostRecentOnly,
			@RequestParam(defaultValue = "0") final Integer page, //
			@RequestParam(defaultValue = "20") final Integer size, //
			@RequestParam(required = false) final Sort sort) {
		Sort sortOrDefault = sort != null ? sort : SORT_DEFAULT;
		PageRequest pageRequest = PageRequest.of(page, size, sortOrDefault);
		Page<PlayerSnapshotDto> playerSnapshotPage = playerSnapshotService //
				.findByClubName(clubName, mostRecentOnly, pageRequest).map(this::snapshotToDto);

		return toPagedModel(playerSnapshotPage);
	}

	@GetMapping(params = { "clubName", "date" })
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerSnapshotDto> byClubNameAndDate(@RequestParam final String clubName,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate date,
			@RequestParam(defaultValue = "0") final Integer page, //
			@RequestParam(defaultValue = "20") final Integer size, //
			@RequestParam(required = false) final Sort sort) {
		Sort sortOrDefault = sort != null ? sort : SORT_DEFAULT;
		PageRequest pageRequest = PageRequest.of(page, size, sortOrDefault);

		Page<PlayerSnapshotDto> playerSnapshotPage = playerSnapshotService //
				.findByClubNameAndCreated(clubName, date, pageRequest) //
				.map(this::snapshotToDto);

		return toPagedModel(playerSnapshotPage);
	}

	@DeleteMapping(value = DELETE, params = "id")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public void deleteById(@RequestParam final Long id) {
		playerSnapshotService.deleteById(id);
	}

	@DeleteMapping(value = DELETE, params = "beforeDate")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public void deleteBeforeDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate beforeDate) {
		playerSnapshotService.deleteBeforeDate(beforeDate);
	}

	private PagedModel<PlayerSnapshotDto> toPagedModel(final Page<PlayerSnapshotDto> page) {
		return PagedModel.of(page.getContent(), //
				new PageMetadata(page.getSize(), //
						page.getNumber(), //
						page.getTotalElements()));
	}

	private PlayerSnapshotDto snapshotToDto(final PlayerSnapshotEntity playerSnapshotEntity) {
		return modelMapper.map(playerSnapshotEntity, PlayerSnapshotDto.class);
	}
}
