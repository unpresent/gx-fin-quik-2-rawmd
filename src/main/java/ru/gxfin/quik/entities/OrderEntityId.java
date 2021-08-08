package ru.gxfin.quik.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * IdClass для {@link OrderEntity}
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class OrderEntityId implements Serializable {

    /**
     * Код биржи в торговой системе
     */
    private String exchangeCode;

    /**
     * Номер заявки в торговой системе
     */
    private String orderNum;
}
