package ru.gxfin.quik.services;

import ru.gxfin.common.data.DataServiceWithTopics;
import ru.gxfin.quik.db.DealEntity;

public interface DealsService extends DataServiceWithTopics<DealEntity> {
    // AllTradeEntity findByExchangeCodeAndTradeNum(String exchageCode, String tradeNum);
}
