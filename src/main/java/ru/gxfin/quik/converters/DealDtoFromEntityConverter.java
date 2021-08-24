package ru.gxfin.quik.converters;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.AbstractDtoFromEntityConverter;
import ru.gxfin.gate.quik.model.internal.QuikDeal;
import ru.gxfin.gate.quik.model.internal.QuikDealsPackage;
import ru.gxfin.gate.quik.model.memdata.QuikDealsMemoryRepository;
import ru.gxfin.quik.entities.DealEntity;

public class DealDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikDeal, QuikDealsPackage, DealEntity> {
    @Autowired
    private QuikDealsMemoryRepository quikDealsMemoryRepository;

    @Override
    public void fillDtoFromEntity(QuikDeal destination, DealEntity source) {
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
                .setPrice2(source.getPrice2())
                .setRepoRate(source.getRepoRate())
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
                .setCanceledDateTime(source.getCanceledDateTime())
                .setClearingFirmId(source.getClearingFirmId())
                .setSystemRef(source.getSystemRef())
                .setUid(source.getUid());
    }

    @Override
    protected QuikDeal getOrCreateDtoByEntity(DealEntity dealEntity) {
        final var id = dealEntity.getExchangeCode() + ":" + dealEntity.getTradeNum();
        var result = this.quikDealsMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikDeal();
        }
        return result;
    }

}
