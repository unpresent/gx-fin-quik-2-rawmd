package ru.gx.fin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gx.fin.quik.entities.DealEntity;
import ru.gx.fin.quik.entities.DealEntityId;

@Repository
public interface DealsRepository extends JpaRepository<DealEntity, DealEntityId> {
}
