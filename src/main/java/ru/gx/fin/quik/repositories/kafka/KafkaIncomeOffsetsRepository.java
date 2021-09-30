package ru.gx.fin.quik.repositories.kafka;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gx.fin.quik.entities.kafka.KafkaIncomeOffsetEntity;
import ru.gx.fin.quik.entities.kafka.KafkaIncomeOffsetEntityId;

@Repository
public interface KafkaIncomeOffsetsRepository extends JpaRepository<KafkaIncomeOffsetEntity, KafkaIncomeOffsetEntityId> {
}
