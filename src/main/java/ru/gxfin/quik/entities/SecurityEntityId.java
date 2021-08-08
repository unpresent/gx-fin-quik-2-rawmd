package ru.gxfin.quik.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * IdClass для {@link SecurityEntity}
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SecurityEntityId implements Serializable {
    /**
     * Код инструмента
     */
    private String code;

    /**
     * Код класса инструментов
     */
    private String classCode;
}
