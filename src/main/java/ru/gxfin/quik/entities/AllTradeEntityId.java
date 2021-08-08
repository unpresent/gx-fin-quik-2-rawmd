package ru.gxfin.quik.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * IdClass для {@link AllTradeEntity}
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class AllTradeEntityId implements Serializable {

    /**
     * Код биржи в торговой системе
     */
    private String exchangeCode;

    /**
     * Номер сделки в торговой системе
     */
    private String tradeNum;
}
