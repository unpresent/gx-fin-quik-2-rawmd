package ru.gxfin.quik.services;

import ru.gxfin.common.data.AbstractDtoEntityConvertor;
import ru.gxfin.common.data.ObjectCreateException;
import ru.gxfin.gate.quik.model.internal.QuikAllTrade;
import ru.gxfin.gate.quik.model.internal.QuikAllTradesPackage;
import ru.gxfin.gate.quik.model.memdata.QuikAllTradesMemoryRepository;
import ru.gxfin.quik.entities.AllTradeEntitiesPackage;
import ru.gxfin.quik.entities.AllTradeEntity;

public class AllTradesTransformer extends AbstractDtoEntityConvertor<QuikAllTrade, QuikAllTradesPackage, AllTradeEntity, AllTradeEntitiesPackage> {
    @Override
    public void setDtoFromEntity(QuikAllTrade destination, AllTradeEntity source) {
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
    public void setEntityFromDto(AllTradeEntity destination, QuikAllTrade source) {
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
    protected QuikAllTrade getOrCreateDtoByEntity(AllTradeEntity allTradeEntity) throws ObjectCreateException {
        final var id = allTradeEntity.getExchangeCode() + ":" + allTradeEntity.getTradeNum();
        return QuikAllTradesMemoryRepository.ObjectsFactory.getOrCreateObject(id);
    }

    @Override
    protected AllTradeEntity getOrCreateEntityByDto(QuikAllTrade quikAllTrade) {
        // В нашем случае пр загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new AllTradeEntity();
    }

}
