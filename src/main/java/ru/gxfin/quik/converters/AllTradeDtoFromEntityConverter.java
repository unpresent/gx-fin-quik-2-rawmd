package ru.gxfin.quik.converters;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.AbstractDtoFromEntityConverter;
import ru.gxfin.gate.quik.model.internal.QuikAllTrade;
import ru.gxfin.gate.quik.model.internal.QuikAllTradesPackage;
import ru.gxfin.gate.quik.model.memdata.QuikAllTradesMemoryRepository;
import ru.gxfin.quik.entities.AllTradeEntity;

public class AllTradeDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikAllTrade, QuikAllTradesPackage, AllTradeEntity> {
    @Autowired
    private QuikAllTradesMemoryRepository allTradesMemoryRepository;

    @Override
    public void fillDtoFromEntity(QuikAllTrade destination, AllTradeEntity source) {
        destination
                .setExchangeCode(source.getExchangeCode())
                .setTradeNum(source.getTradeNum())
                .setDirection(source.getDirection())
                .setTradeDateTime(source.getTradeDateTime())
                .setClassCode(source.getClassCode())
                .setPrice(source.getPrice())
                .setQuantity(source.getQuantity())
                .setValue(source.getValue())
                .setAccruedInterest(source.getAccruedInterest())
                .setYield(source.getYield())
                .setSettleCode(source.getSettleCode())
                .setRepoRate(source.getRepoRate())
                .setRepoValue(source.getRepoValue())
                .setRepo2Value(source.getRepo2Value())
                .setRepoTerm(source.getRepoTerm())
                .setPeriod(source.getPeriod())
                .setOpenInterest(source.getOpenInterest());
    }


    @Override
    protected QuikAllTrade getOrCreateDtoByEntity(AllTradeEntity source) {
        final var id = source.getExchangeCode() + ":" + source.getTradeNum();
        var result = this.allTradesMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikAllTrade();
        }
        return result;
    }
}
