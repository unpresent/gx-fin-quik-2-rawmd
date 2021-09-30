package ru.gx.fin.quik.entities;

import lombok.*;
import lombok.experimental.Accessors;
import ru.gx.data.jpa.AbstractEntityObject;
import ru.gx.fin.gate.quik.model.internal.QuikDealDirection;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@IdClass(AllTradeEntityId.class)
@Table(schema = "Quik", name = "AllTrades")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor(access = PUBLIC)
public class AllTradeEntity extends AbstractEntityObject {

    /**
     * Код биржи в торговой системе
     */
    @Id
    @Column(name = "ExchangeCode", length = 50, nullable = false)
    private String exchangeCode;

    /**
     * Номер сделки в торговой системе
     */
    @Id
    @Column(name = "TradeNum", length = 50, nullable = false)
    private String tradeNum;

    /**
     * Набор битовых флагов
     */
    @Column(name = "Direction")
    private QuikDealDirection direction;

    /**
     * Дата и время
     */
    @Column(name = "TradeDateTime", nullable = false)
    private LocalDateTime tradeDateTime;

    /**
     * Код класса
     */
    @Column(name = "ClassCode", length = 50)
    private String classCode;

    /**
     * Код бумаги заявки
     */
    @Column(name = "SecCode", length = 50)
    private String secCode;

    /**
     * Цена
     */
    @Column(name = "Price", precision = 24, scale = 8)
    private BigDecimal price;

    /**
     * Количество бумаг в последней сделке в лотах
     */
    @Column(name = "Quantity", precision = 24, scale = 8)
    private BigDecimal quantity;

    /**
     * Объем в денежных средствах
     */
    @Column(name = "Value", precision = 24, scale = 8)
    private BigDecimal value;

    /**
     * Накопленный купонный доход
     */
    @Column(name = "AccruedInterest", precision = 24, scale = 8)
    private BigDecimal accruedInterest;

    /**
     * Доходность
     */
    @Column(name = "Yield", precision = 24, scale = 8)
    private BigDecimal yield;

    /**
     * Код расчетов
     */
    @Column(name = "SettleCode", length = 50, nullable = false)
    private String settleCode;

    /**
     * Ставка РЕПО (%)
     */
    @Column(name = "RepoRate", precision = 24, scale = 8)
    private BigDecimal repoRate;

    /**
     * Сумма РЕПО
     */
    @Column(name = "RepoValue", precision = 24, scale = 8)
    private BigDecimal repoValue;

    /**
     * Объем выкупа РЕПО
     */
    @Column(name = "Repo2Value", precision = 24, scale = 8)
    private BigDecimal repo2Value;

    /**
     * Срок РЕПО в днях
     */
    @Column(name = "RepoTerm")
    private int repoTerm;

    /**
     * Период торговой сессии. Возможные значения:
     * «0» – Открытие;
     * «1» – Нормальный;
     * «2» – Закрытие
     */
    @Column(name = "Period")
    private short period;

    /**
     * Открытый интерес
     */
    @Column(name = "OpenInterest")
    private int openInterest;
}