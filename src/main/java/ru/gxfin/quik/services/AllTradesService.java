package ru.gxfin.quik.services;

import ru.gxfin.common.data.DataServiceWithTopics;
import ru.gxfin.quik.db.AllTradeEntity;

public interface AllTradesService extends DataServiceWithTopics<AllTradeEntity> {
    // AllTradeEntity findByExchangeCodeAndTradeNum(String exchageCode, String tradeNum);
}
