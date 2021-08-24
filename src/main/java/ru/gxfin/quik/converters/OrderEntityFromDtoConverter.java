package ru.gxfin.quik.converters;

import ru.gxfin.common.data.AbstractEntityFromDtoConverter;
import ru.gxfin.gate.quik.model.internal.QuikOrder;
import ru.gxfin.quik.entities.OrderEntitiesPackage;
import ru.gxfin.quik.entities.OrderEntity;

public class OrderEntityFromDtoConverter extends AbstractEntityFromDtoConverter<OrderEntity, OrderEntitiesPackage, QuikOrder> {
    @Override
    public void fillEntityFromDto(OrderEntity destination, QuikOrder source) {
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
    protected OrderEntity getOrCreateEntityByDto(QuikOrder quikOrder) {
        // final var optionalEntity = this.OrdersRepository.findByExchangeCodeAndTradeNum(quikOrder.getExchangeCode(), quikOrder.getTradeNum());
        // return optionalEntity.isEmpty() ? new OrderEntity() : optionalEntity.get();

        // В нашем случае пр загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new OrderEntity();
    }
}
