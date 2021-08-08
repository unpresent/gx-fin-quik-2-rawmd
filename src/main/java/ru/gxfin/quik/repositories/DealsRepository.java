package ru.gxfin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gxfin.quik.entities.DealEntity;
import ru.gxfin.quik.entities.DealEntityId;

@Repository
public interface DealsRepository extends JpaRepository<DealEntity, DealEntityId> {
}
