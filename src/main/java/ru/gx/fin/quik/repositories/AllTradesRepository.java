package ru.gx.fin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gx.fin.quik.entities.AllTradeEntity;
import ru.gx.fin.quik.entities.AllTradeEntityId;

@Repository
public interface AllTradesRepository extends JpaRepository<AllTradeEntity, AllTradeEntityId> {
}
