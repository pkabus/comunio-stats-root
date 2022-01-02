package pkabus.comuniostatsbackend.service;

import java.util.List;
import java.util.stream.Stream;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface FlatPlayerSnapshotService {

	List<PlayerSnapshotEntity> saveAll(Stream<PlayerSnapshotEntity> map);

	PlayerSnapshotEntity getOrSave(PlayerSnapshotEntity playerSnapshot);

}
