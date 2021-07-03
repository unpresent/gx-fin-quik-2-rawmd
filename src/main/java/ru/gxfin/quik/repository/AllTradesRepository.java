package ru.gxfin.quik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gxfin.quik.db.AllTradeEntity;

public interface AllTradesRepository extends JpaRepository<AllTradeEntity, Long> {
    // Optional<AllTradeEntity> findByExchangeCodeAndTradeNum(String exchangeCode, String tradeNum);
}
