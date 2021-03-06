package pkabus.comuniostatsbackend.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.mapper.PlayerConverter;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.service.PlayerService;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;

@RestController
@RequestMapping(PlayerController.BASE_PLAYERS)
public class PlayerController {

	public static final String BASE_PLAYERS = "/players";
	public static final String ALL = "/all";
	public static final String CREATE = "/create";
	public static final String DELETE = "/delete";

	private final PlayerService playerService;

	private final PlayerConverter playerConverter;

	public PlayerController(final PlayerService playerService, final PlayerConverter playerConverter) {
		super();
		this.playerService = playerService;
		this.playerConverter = playerConverter;
	}

	@GetMapping(value = ALL)
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerDto> all(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "20") final int size) {
		PageRequest pageRequest = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));

		Page<PlayerDto> playerPage = playerService.findAll(pageRequest).map(playerConverter::toDto);

		return PagedModel.of(playerPage.getContent(),
				new PageMetadata(playerPage.getSize(), playerPage.getNumber(), playerPage.getTotalElements()));
	}

	@GetMapping(params = "id")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PlayerDto byId(@RequestParam final Long id) {
		PlayerEntity playerEntity = playerService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return playerConverter.toDto(playerEntity);
	}

	@GetMapping(params = "name")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerDto> byName(@RequestParam final String name,
			@RequestParam(defaultValue = "0") final Integer page,
			@RequestParam(defaultValue = "20") final Integer size) {
		Page<PlayerDto> playerPage = playerService.findByName(name, PageRequest.of(page, size)) //
				.map(playerConverter::toDto);

		return PagedModel.of(playerPage.getContent(),
				new PageMetadata(playerPage.getSize(), playerPage.getNumber(), playerPage.getTotalElements()));
	}

	@GetMapping(path = "q", params = "name")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<PlayerDto> byNameContains(@RequestParam final String name,
			@RequestParam(defaultValue = "0") final Integer page,
			@RequestParam(defaultValue = "20") final Integer size) {
		Page<PlayerDto> playerPage = playerService.findByNameContains(name, PageRequest.of(page, size)) //
				.map(playerConverter::toDto);

		return PagedModel.of(playerPage.getContent(),
				new PageMetadata(playerPage.getSize(), playerPage.getNumber(), playerPage.getTotalElements()));
	}

	@GetMapping(params = "link")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PlayerDto byLink(@RequestParam final String link) {
		PlayerEntity playerEntity = playerService.findByLink(link)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return playerConverter.toDto(playerEntity);
	}

	@PostMapping(CREATE)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody final PlayerDto player) {
		playerService.save(playerConverter.toEntity(player));
	}

	@DeleteMapping(value = DELETE, params = "link")
	public void deleteByLink(@RequestParam final String link) {
		playerService.deleteByLink(link);
	}

	public void delete(final PlayerDto player) {
		playerService.delete(playerConverter.toEntity(player));
	}

	public void deleteAll() {
		playerService.deleteAll();
	}
}
