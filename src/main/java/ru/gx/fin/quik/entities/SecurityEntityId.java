package ru.gx.fin.quik.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
