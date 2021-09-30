package ru.gx.fin.quik.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.data.jpa.AbstractDtoFromEntityConverter;
import ru.gx.fin.gate.quik.model.internal.QuikDeal;
import ru.gx.fin.gate.quik.model.internal.QuikDealsPackage;
import ru.gx.fin.gate.quik.model.memdata.QuikDealsMemoryRepository;
import ru.gx.fin.quik.entities.DealEntity;

import static lombok.AccessLevel.PROTECTED;

public class DealDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikDeal, QuikDealsPackage, DealEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private QuikDealsMemoryRepository quikDealsMemoryRepository;

    @Override
    public void fillDtoFromEntity(@NotNull final QuikDeal destination, @NotNull final DealEntity source) {
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
    @NotNull
    protected QuikDeal getOrCreateDtoByEntity(@NotNull final DealEntity dealEntity) {
        final var id = dealEntity.getExchangeCode() + ":" + dealEntity.getTradeNum();
        var result = this.quikDealsMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikDeal();
        }
        return result;
    }

}
