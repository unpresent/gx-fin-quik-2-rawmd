package ru.gxfin.quik.services;

import ru.gxfin.common.data.AbstractDtoEntityConvertor;
import ru.gxfin.common.data.ObjectCreateException;
import ru.gxfin.gate.quik.model.internal.QuikOrder;
import ru.gxfin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gxfin.gate.quik.model.memdata.QuikOrdersMemoryRepository;
import ru.gxfin.quik.entities.OrderEntitiesPackage;
import ru.gxfin.quik.entities.OrderEntity;

public class OrdersTransformer extends AbstractDtoEntityConvertor<QuikOrder, QuikOrdersPackage, OrderEntity, OrderEntitiesPackage> {
    @Override
    public void setDtoFromEntity(QuikOrder destination, OrderEntity source) {
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
    public void setEntityFromDto(OrderEntity destination, QuikOrder source) {
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
    protected QuikOrder getOrCreateDtoByEntity(OrderEntity orderEntity) throws ObjectCreateException {
        final var id = orderEntity.getExchangeCode() + ":" + orderEntity.getOrderNum();
        return QuikOrdersMemoryRepository.ObjectsFactory.getOrCreateObject(id);
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
