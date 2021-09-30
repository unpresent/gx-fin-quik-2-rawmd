package ru.gx.fin.quik.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.fin.quik.entities.OrderEntity;
import ru.gx.data.jpa.AbstractDtoFromEntityConverter;
import ru.gx.fin.gate.quik.model.internal.QuikOrder;
import ru.gx.fin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gx.fin.gate.quik.model.memdata.QuikOrdersMemoryRepository;

import static lombok.AccessLevel.PROTECTED;

public class OrderDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikOrder, QuikOrdersPackage, OrderEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private QuikOrdersMemoryRepository quikOrdersMemoryRepository;

    @Override
    public void fillDtoFromEntity(@NotNull final QuikOrder destination, @NotNull final OrderEntity source) {
        destination
                .setExchangeCode(source.getExchangeCode())
                .setOrderNum(source.getOrderNum())
                .setDirection(source.getDirection())
                .setBrokerRef(source.getBrokerRef())
                .setUserId(source.getUserId())
                .setFirmId(source.getFirmId())
                .setAccount(source.getAccount())
                .setPrice(source.getPrice())
                .setQuantity(source.getQuantity())
                .setBalance(source.getBalance())
                .setValue(source.getValue())
                .setAccruedInterest(source.getAccruedInterest())
                .setYield(source.getYield())
                .setTransactionId(source.getTransactionId())
                .setClientCode(source.getClientCode())
                .setPrice2(source.getPrice2())
                .setSettleCode(source.getSettleCode())
                .setUid(source.getUid())
                .setCanceledUid(source.getCanceledUid())
                .setActivationTime(source.getActivationTime())
                .setLinkedOrder(source.getLinkedOrder())
                .setExpiry(source.getExpiry())
                .setSecCode(source.getSecCode())
                .setClassCode(source.getClassCode())
                .setTradeDateTime(source.getTradeDateTime())
                .setWithdrawDateTime(source.getWithdrawDateTime())
                .setBankAccountId(source.getBankAccountId())
                .setValueEntryType(source.getValueEntryType())
                .setRepoTerm(source.getRepoTerm())
                .setRepoValue(source.getRepoValue())
                .setRepo2Value(source.getRepo2Value())
                .setRepoValueBalance(source.getRepoValueBalance())
                .setStartDiscount(source.getStartDiscount())
                .setRejectReason(source.getRejectReason())
                .setExtOrderFlags(source.getExtOrderFlags())
                .setMinQuantity(source.getMinQuantity())
                .setExecType(source.getExecType())
                .setSideQualifier(source.getSideQualifier())
                .setAccountType(source.getAccountType())
                .setCapacity(source.getCapacity())
                .setPassiveOnlyOrder(source.getPassiveOnlyOrder())
                .setVisible(source.getVisible());
    }

    @Override
    @NotNull
    protected QuikOrder getOrCreateDtoByEntity(@NotNull final OrderEntity orderEntity) {
        final var id = orderEntity.getExchangeCode() + ":" + orderEntity.getOrderNum();
        var result = this.quikOrdersMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikOrder();
        }
        return result;
    }
}
