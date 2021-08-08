package ru.gxfin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gxfin.quik.entities.KafkaOffsetEntity;
import ru.gxfin.quik.entities.KafkaOffsetEntityId;

@Repository
public interface KafkaOffsetsRepository extends JpaRepository<KafkaOffsetEntity, KafkaOffsetEntityId> {
}
