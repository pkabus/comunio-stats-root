package pkabus.comuniostatsbackend.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final PlayerRepository playerRepo;

	public PlayerServiceImpl(final PlayerRepository playerRepository) {
		this.playerRepo = playerRepository;
	}

	@Override
	public Page<PlayerEntity> findAll(final Pageable page) {
		return playerRepo.findAll(page);
	}

	@Override
	public Optional<PlayerEntity> findById(final Long id) {
		return playerRepo.findById(id);
	}

	@Override
	public PlayerEntity save(final PlayerEntity player) {
		Optional<PlayerEntity> playerByComunioId = playerRepo.findByLink(player.getLink());

		if (playerByComunioId.isPresent()) {
			logger.warn(String.format("Player '%s' with unique link '%s' is already present. Not created again.",
					playerByComunioId.get().getName(), playerByComunioId.get().getLink()));
		}

		return playerByComunioId.orElseGet(() -> playerRepo.save(player));
	}

	@Override
	public void delete(final PlayerEntity player) {
		playerRepo.delete(player);
	}

	@Override
	public void deleteAll() {
		playerRepo.deleteAll();
	}

	@Override
	public void deleteByLink(final String link) {
		playerRepo.findByLink(link).ifPresentOrElse(playerRepo::delete,
				() -> logger.info(String.format("Player with comunioId '%s' not found. Cannot be deleted.", link)));
	}

	@Override
	public Page<PlayerEntity> findByName(final String name, final Pageable page) {
		return playerRepo.findByName(name, page);
	}

	@Override
	public Optional<PlayerEntity> findByLink(final String link) {
		return playerRepo.findByLink(link);
	}
}
