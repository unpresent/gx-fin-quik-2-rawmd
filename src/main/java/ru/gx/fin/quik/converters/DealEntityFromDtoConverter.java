package ru.gx.fin.quik.converters;

import org.jetbrains.annotations.NotNull;
import ru.gx.data.jpa.AbstractEntityFromDtoConverter;
import ru.gx.fin.gate.quik.model.internal.QuikDeal;
import ru.gx.fin.quik.entities.DealEntitiesPackage;
import ru.gx.fin.quik.entities.DealEntity;

public class DealEntityFromDtoConverter extends AbstractEntityFromDtoConverter<DealEntity, DealEntitiesPackage, QuikDeal> {
    @Override
    public void fillEntityFromDto(@NotNull final DealEntity destination, @NotNull final QuikDeal source) {
        destination
                .setExchangeCode(source.getExchangeCode())
                .setTradeNum(source.getTradeNum())
                .setOrderNum(source.getOrderNum())
                .setBrokerRef(source.getBrokerRef())
                .setUserId(source.getUserId())
                .setFirmId(source.getFirmId())
                .setCanceledUid(source.getCanceledUid())
                .setAccount(source.getAccount())
                .setPrice(source.getPrice())
                .setQuantity(source.getQuantity())
                .setValue(source.getValue())
                .setAccruedInterest(source.getAccruedInterest())
                .setYield(source.getYield())
                .setSettleCode(source.getSettleCode())
                .setCpFirmId(source.getCpFirmId())
                .setDirection(source.getDirection())
                .setAccrued2(source.getAccrued2())
                .setRepoTerm(source.getRepoTerm())
                .setRepoValue(source.getRepoValue())
                .setRepo2Value(source.getRepo2Value())
                .setStartDiscount(source.getStartDiscount())
                .setLowerDiscount(source.getLowerDiscount())
                .setUpperDiscount(source.getUpperDiscount())
                .setBlockSecurities(source.getBlockSecurities())
                .setClearingComission(source.getClearingComission())
                .setExchangeComission(source.getExchangeComission())
                .setTechCenterComission(source.getTechCenterComission())
                .setSettleDate(source.getSettleDate())
                .setSettleCurrency(source.getSettleCurrency())
                .setTradeCurrency(source.getTradeCurrency())
                .setStationId(source.getStationId())
                .setSecCode(source.getSecCode())
                .setClassCode(source.getClassCode())
                .setTradeDateTime(source.getTradeDateTime())
                .setBankAccountId(source.getBankAccountId())
                .setBrokerComission(source.getBrokerComission())
                .setLinkedTrade(source.getLinkedTrade())
                .setPeriod(source.getPeriod())
                .setTransactionId(source.getTransactionId())
                .setKind(source.getKind())
                .setClearingBankAccountId(source.getClearingBankAccountId())
                .setClearingFirmId(source.getClearingFirmId())
                .setSystemRef(source.getSystemRef())
                .setUid(source.getUid());
    }

    @Override
    @NotNull
    protected DealEntity getOrCreateEntityByDto(@NotNull final QuikDeal quikDeal) {
        // В нашем случае при загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new DealEntity();
    }
}
