package ru.gx.fin.quik.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;
import ru.gx.data.jpa.AbstractEntityObject;

import javax.persistence.*;
import java.math.BigDecimal;

import static lombok.AccessLevel.PUBLIC;

/**
 * Сделка
 */
@Entity
@IdClass(SecurityEntityId.class)
@Table(schema = "Quik", name = "Securities")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor(access = PUBLIC)
public class SecurityEntity extends AbstractEntityObject {
    /**
     * Код инструмента
     */
    @Id
    @Column(name = "Code", length = 50, nullable = false)
    private String code;

    /**
     * Код класса инструментов
     */
    @Id
    @Column(name = "ClassCode", length = 20)
    private String classCode;

    /**
     * Наименование инструмента
     */
    @Column(name = "Name", length = 250)
    private String name;

    /**
     * Короткое наименование инструмента
     */
    @Column(name = "ShortName", length = 100)
    private String shortName;

    /**
     * Наименование класса инструментов
     */
    @Column(name = "ClassName", length = 100)
    private String className;

    /**
     * Номинал
     */
    @Column(name = "FaceValue", precision = 24, scale = 8)
    private BigDecimal faceValue;

    /**
     * Валюта номинала
     */
    @Column(name = "FaceUnit", length = 3)
    @Nullable
    private String faceUnit;

    /**
     * Точность (количество значащих цифр после запятой)
     */
    @Column(name = "Scale")
    private int scale;

    /**
     * Дата погашения
     */
    @Column(name = "MaturityDate")
    private long maturityDate;

    /**
     * Размер лота
     */
    @Column(name = "LotSize", precision = 24, scale = 8)
    private BigDecimal lotSize;

    /**
     * ISIN
     */
    @Column(name = "IsinCode", length = 20)
    private String isinCode;

    /**
     * Минимальный шаг цены
     */
    @Column(name = "MinPriceStep", precision = 24, scale = 8)
    private BigDecimal minPriceStep;
}
