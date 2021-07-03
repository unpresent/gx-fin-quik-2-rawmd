package ru.gxfin.quik.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.gxfin.common.data.AbstractIdentifiedDataObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table( schema = "Quik", name = "AllTrades")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AllTradeEntity extends AbstractIdentifiedDataObject<Long> {
    /**
     * Порядковый номер сделки
     */
    @JsonProperty
    private int rowIndex;

    /**
     * Номер сделки в торговой системе
     */
    @JsonProperty
    @Column(name = "TradeNum", length = 50, nullable = false)
    private String tradeNum;

    /**
     * Набор битовых флагов
     */
    @JsonProperty
    @Column(name = "Flags")
    private int flags;

    /**
     * Дата и время
     */
    @JsonProperty
    @Column(name = "TradeDateTime", nullable = false)
    private LocalDateTime tradeDateTime;

    /**
     * Код биржи в торговой системе
     */
    @JsonProperty
    @Column(name = "ExchangeCode", length = 50, nullable = false)
    private String exchangeCode;

    /**
     * Код класса
     */
    @JsonProperty
    @Column(name = "SlassCode", length = 50)
    private String classCode;

    /**
     * Код бумаги заявки
     */
    @JsonProperty
    @Column(name = "SecCode", length = 50)
    private String secCode;

    /**
     * Цена
     */
    @JsonProperty
    @Column(name = "Price", precision = 24, scale = 8)
    private BigDecimal price;

    /**
     * Количество бумаг в последней сделке в лотах
     */
    @JsonProperty
    @Column(name = "Quantity", precision = 24, scale = 8)
    private BigDecimal quantity;

    /**
     * Объем в денежных средствах
     */
    @JsonProperty
    @Column(name = "Value", precision = 24, scale = 8)
    private BigDecimal value;

    /**
     * Накопленный купонный доход
     */
    @JsonProperty
    @Column(name = "AccruedInterest", precision = 24, scale = 8)
    private BigDecimal accruedInterest;

    /**
     * Доходность
     */
    @JsonProperty
    @Column(name = "Yield", precision = 24, scale = 8)
    private BigDecimal yield;

    /**
     * Код расчетов
     */
    @JsonProperty
    @Column(name = "SettleCode", length = 50, nullable = false)
    private String settleCode;

    /**
     * Ставка РЕПО (%)
     */
    @JsonProperty
    @Column(name = "RepoRate", precision = 24, scale = 8)
    private BigDecimal repoRate;

    /**
     * Сумма РЕПО
     */
    @JsonProperty
    @Column(name = "RepoValue", precision = 24, scale = 8)
    private BigDecimal repoValue;

    /**
     * Объем выкупа РЕПО
     */
    @JsonProperty
    @Column(name = "Repo2Value", precision = 24, scale = 8)
    private BigDecimal repo2Value;

    /**
     * Срок РЕПО в днях
     */
    @JsonProperty
    @Column(name = "RepoTerm")
    private int repoTerm;

    /**
     * Период торговой сессии. Возможные значения:
     * «0» – Открытие;
     * «1» – Нормальный;
     * «2» – Закрытие
     */
    @JsonProperty
    @Column(name = "Period")
    private short period;

    /**
     * Открытый интерес
     */
    @JsonProperty
    @Column(name = "OpenInterest")
    private int openInterest;
}