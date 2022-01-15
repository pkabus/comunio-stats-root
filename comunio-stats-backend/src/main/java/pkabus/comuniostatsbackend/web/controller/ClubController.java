package pkabus.comuniostatsbackend.web.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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

import pkabus.comuniostatsbackend.mapper.ClubConverter;
import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.service.ClubService;
import pkabus.comuniostatsbackend.web.dto.ClubDto;

@RestController
@RequestMapping(ClubController.BASE_CLUBS)
public class ClubController {

	public static final String BASE_CLUBS = "/clubs";
	public static final String CREATE = "/create";

	private final ClubService clubService;

	private final ClubConverter clubConverter;

	public ClubController(final ClubService clubService, final ClubConverter clubConverter) {
		super();
		this.clubService = clubService;
		this.clubConverter = clubConverter;
	}

	@GetMapping
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public List<ClubDto> ofNow() {
		return ofDate(LocalDate.now());
	}

	@GetMapping(params = "date")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public List<ClubDto> ofDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
	@RequestParam final LocalDate date) {
		List<ClubDto> clubList = clubService.findAllOfDate(date) //
				.stream() //
				.map(clubConverter::toDto) //
				.collect(Collectors.toList());

		return clubList;
	}

	@GetMapping(value = "/all")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
					// going to run! Should be a property
	public PagedModel<ClubDto> all(@RequestParam(defaultValue = "0") final Integer page,
			@RequestParam(defaultValue = "20") final Integer size) {
		Page<ClubDto> clubPage = clubService.findAll(PageRequest.of(page, size)).map(clubConverter::toDto);

		return PagedModel.of(clubPage.getContent(),
				new PageMetadata(clubPage.getSize(), clubPage.getNumber(), clubPage.getTotalElements()));
	}

	@GetMapping(params = "id")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public ClubDto byId(@RequestParam final Long id) {
		ClubEntity clubEntity = clubService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return clubConverter.toDto(clubEntity);
	}

	@GetMapping(params = "name")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public ClubDto byName(@RequestParam final String name) {
		ClubEntity clubEntity = clubService.findByName(name)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return clubConverter.toDto(clubEntity);
	}

	@GetMapping(path = "q", params = "name")
	@CrossOrigin // to enable frontend requests on same host, TODO set domain where frontend is
	// going to run! Should be a property
	public PagedModel<ClubDto> byNameContains(@RequestParam final String name,
			@RequestParam(defaultValue = "0") final Integer page,
			@RequestParam(defaultValue = "20") final Integer size) {
		Page<ClubDto> clubPage = clubService.findByNameContains(name, PageRequest.of(page, size))
				.map(clubConverter::toDto);

		return PagedModel.of(clubPage.getContent(), new PageMetadata(clubPage.getSize(), clubPage.getNumber(), clubPage.getTotalElements()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody final ClubDto club) {
		clubService.save(clubConverter.toEntity(club));
	}

	@DeleteMapping(value = "/delete", params = "byName")
	public void deleteByName(@RequestParam final String byName) {
		clubService.deleteByName(byName);
	}

	public void delete(final ClubDto club) {
		clubService.delete(clubConverter.toEntity(club));
	}

	public void deleteAll() {
		clubService.deleteAll();
	}

}
