package ru.gxfin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gxfin.quik.entities.AllTradeEntity;
import ru.gxfin.quik.entities.AllTradeEntityId;

import java.util.Optional;

@Repository
public interface AllTradesRepository extends JpaRepository<AllTradeEntity, AllTradeEntityId> {
}
