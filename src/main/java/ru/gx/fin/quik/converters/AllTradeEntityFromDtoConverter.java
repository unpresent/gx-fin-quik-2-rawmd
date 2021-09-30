package ru.gx.fin.quik.converters;

import org.jetbrains.annotations.NotNull;
import ru.gx.fin.quik.entities.AllTradeEntity;
import ru.gx.data.jpa.AbstractEntityFromDtoConverter;
import ru.gx.fin.gate.quik.model.internal.QuikAllTrade;
import ru.gx.fin.quik.entities.AllTradeEntitiesPackage;

public class AllTradeEntityFromDtoConverter extends AbstractEntityFromDtoConverter<AllTradeEntity, AllTradeEntitiesPackage, QuikAllTrade> {
    @Override
    public void fillEntityFromDto(@NotNull final AllTradeEntity destination, @NotNull final QuikAllTrade source) {
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
    @NotNull
    protected AllTradeEntity getOrCreateEntityByDto(@NotNull final QuikAllTrade quikAllTrade) {
        // В нашем случае при загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new AllTradeEntity();
    }

}
