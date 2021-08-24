package ru.gxfin.quik.converters;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.AbstractDtoFromEntityConverter;
import ru.gxfin.gate.quik.model.internal.QuikOrder;
import ru.gxfin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gxfin.gate.quik.model.memdata.QuikOrdersMemoryRepository;
import ru.gxfin.quik.entities.OrderEntity;

public class OrderDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikOrder, QuikOrdersPackage, OrderEntity> {
    @Autowired
    private QuikOrdersMemoryRepository quikOrdersMemoryRepository;

    @Override
    public void fillDtoFromEntity(QuikOrder destination, OrderEntity source) {
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
    protected QuikOrder getOrCreateDtoByEntity(OrderEntity orderEntity) {
        final var id = orderEntity.getExchangeCode() + ":" + orderEntity.getOrderNum();
        var result = this.quikOrdersMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikOrder();
        }
        return result;
    }
}
