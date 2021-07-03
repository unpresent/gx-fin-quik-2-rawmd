package ru.gxfin.quik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gxfin.quik.db.AllTradeEntity;
import ru.gxfin.quik.db.DealEntity;

public interface DealsRepository extends JpaRepository<DealEntity, Long> {
}
