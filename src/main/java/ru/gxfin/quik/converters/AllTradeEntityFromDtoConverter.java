package ru.gxfin.quik.converters;

import ru.gxfin.common.data.AbstractEntityFromDtoConverter;
import ru.gxfin.gate.quik.model.internal.QuikAllTrade;
import ru.gxfin.quik.entities.AllTradeEntitiesPackage;
import ru.gxfin.quik.entities.AllTradeEntity;

public class AllTradeEntityFromDtoConverter extends AbstractEntityFromDtoConverter<AllTradeEntity, AllTradeEntitiesPackage, QuikAllTrade> {
    @Override
    public void fillEntityFromDto(AllTradeEntity destination, QuikAllTrade source) {
        destination
                .setExchangeCode(source.getExchangeCode())
                .setTradeNum(source.getTradeNum())
                .setDirection(source.getDirection())
                .setTradeDateTime(source.getTradeDateTime())
                .setClassCode(source.getClassCode())
                .setSecCode(source.getSecCode())
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
    protected AllTradeEntity getOrCreateEntityByDto(QuikAllTrade quikAllTrade) {
        // В нашем случае при загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new AllTradeEntity();
    }

}
