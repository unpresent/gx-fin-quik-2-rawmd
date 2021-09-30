package ru.gx.fin.quik.converters;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.fin.quik.entities.AllTradeEntity;
import ru.gx.data.jpa.AbstractDtoFromEntityConverter;
import ru.gx.fin.gate.quik.model.internal.QuikAllTrade;
import ru.gx.fin.gate.quik.model.internal.QuikAllTradesPackage;
import ru.gx.fin.gate.quik.model.memdata.QuikAllTradesMemoryRepository;

import static lombok.AccessLevel.*;

public class AllTradeDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikAllTrade, QuikAllTradesPackage, AllTradeEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private QuikAllTradesMemoryRepository allTradesMemoryRepository;

    @Override
    public void fillDtoFromEntity(@NotNull final QuikAllTrade destination, @NotNull final AllTradeEntity source) {
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
    @NotNull
    protected QuikAllTrade getOrCreateDtoByEntity(@NotNull final AllTradeEntity source) {
        final var id = source.getExchangeCode() + ":" + source.getTradeNum();
        var result = this.allTradesMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikAllTrade();
        }
        return result;
    }
}
