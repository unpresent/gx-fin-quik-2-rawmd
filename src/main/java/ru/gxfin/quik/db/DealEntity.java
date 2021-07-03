package ru.gxfin.quik.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.gxfin.common.data.AbstractIdentifiedDataObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Сделка
 */
@Entity
@Table( schema = "Quik", name = "Deals")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DealEntity extends AbstractIdentifiedDataObject<Long> {
    /**
     * Номер сделки в торговой системе
     */
    @Column(name = "TradeNum", length = 50, nullable = false)
    private String tradeNum;

    /**
     * Номер заявки в торговой системе
     */
    @Column(name = "OrderNum", length = 50, nullable = true)
    private String orderNum;

    /**
     * Комментарий, обычно: <код клиента>/<номер поручения>
     */
    @Column(name = "BrokerRef", length = 50, nullable = true)
    private String brokerRef;

    /**
     * Идентификатор трейдера
     */
    @Column(name = "UserId", length = 50, nullable = true)
    private String userId;

    /**
     * Идентификатор фирмы
     */
    @Column(name = "FirmId", length = 50, nullable = true)
    private String firmId;

    /**
     * Идентификатор пользователя, снявшего заявку
     */
    @Column(name = "CanceledUid", nullable = true)
    private long canceledUid;

    /**
     * Торговый счет
     */
    @Column(name = "Account", length = 50, nullable = true)
    private String account;

    /**
     * Цена
     */
    @Column(name = "Price", precision = 24, scale = 8, nullable = true)
    private BigDecimal price;

    /**
     * Количество в лотах
     */
    @Column(name = "Quantity", precision = 24, scale = 8, nullable = false)
    private BigDecimal quantity;

    /**
     * Объем в денежных средствах
     */
    @Column(name = "Value", precision = 24, scale = 8, nullable = false)
    private BigDecimal value;

    /**
     * Накопленный купонный доход
     */
    @Column(name = "AccruedInterest", precision = 24, scale = 8, nullable = true)
    private BigDecimal accruedInterest;

    /**
     * Доходность
     */
    @Column(name = "Yield", precision = 24, scale = 8, nullable = true)
    private BigDecimal yield;

    /**
     * Код расчетов
     */
    @Column(name = "SettleCode", length = 50, nullable = true)
    private String settleCode;

    /**
     * Код фирмы партнера
     */
    @Column(name = "CpFirmId", length = 50, nullable = true)
    private String cpFirmId;

    /**
     * Набор битовых флагов
     */
    @Column(name = "Flags", nullable = true)
    private int flags;

    /**
     * Цена выкупа
     */
    @Column(name = "Price2", precision = 24, scale = 8, nullable = true)
    private BigDecimal price2;

    /**
     * Ставка РЕПО (%)
     */
    @Column(name = "RepoRate", precision = 24, scale = 8, nullable = true)
    private BigDecimal repoRate;

    /**
     * Код клиента
     */
    @Column(name = "ClientCode", length = 50, nullable = true)
    private String clientCode;

    /**
     * Доход (%) на дату выкупа
     */
    @Column(name = "Accrued2", precision = 24, scale = 8, nullable = true)
    private BigDecimal accrued2;

    /**
     * Срок РЕПО, в календарных днях
     */
    @Column(name = "RepoTerm", nullable = true)
    private int repoTerm;

    /**
     * Сумма РЕПО на текущую дату. Отображается с точностью 2 знака
     */
    @Column(name = "RepoValue", precision = 24, scale = 8, nullable = true)
    private BigDecimal repoValue;

    /**
     * Объём сделки выкупа РЕПО. Отображается с точностью 2 знака
     */
    @Column(name = "Repo2Value", precision = 24, scale = 8, nullable = true)
    private BigDecimal repo2Value;

    /**
     * Начальный дисконт (%)
     */
    @Column(name = "StartDiscount", precision = 24, scale = 8, nullable = true)
    private BigDecimal startDiscount;

    /**
     * Нижний дисконт (%)
     */
    @Column(name = "LowerDiscount", precision = 24, scale = 8, nullable = true)
    private BigDecimal lowerDiscount;

    /**
     * Верхний дисконт (%)
     */
    @Column(name = "UpperDiscount", precision = 24, scale = 8, nullable = true)
    private BigDecimal upperDiscount;

    /**
     * Блокировка обеспечения («Да»/«Нет»)
     */
    @Column(name = "BlockSecurities", precision = 24, scale = 8, nullable = true)
    private BigDecimal blockSecurities;

    /**
     * Клиринговая комиссия (ММВБ)
     */
    @Column(name = "ClearingComissio", precision = 24, scale = 8, nullable = true)
    private BigDecimal clearingComission;

    /**
     * Комиссия Фондовой биржи (ММВБ)
     */
    @Column(name = "ExchangeComission", precision = 24, scale = 8, nullable = true)
    private BigDecimal exchangeComission;

    /**
     * Комиссия Технического центра (ММВБ)
     */
    @Column(name = "TechCenterComission", precision = 24, scale = 8, nullable = true)
    private BigDecimal techCenterComission;

    /**
     * Дата расчетов
     */
    @Column(name = "SettleDate", nullable = true)
    private int settleDate;

    /**
     * Валюта расчетов
     */
    @Column(name = "SettleCurrency", length = 10, nullable = true)
    private String settleCurrency;

    /**
     * Валюта
     */
    @Column(name = "TradeCurrency", length = 10, nullable = true)
    private String tradeCurrency;

    /**
     * Код биржи в торговой системе
     */
    @Column(name = "ExchangeCode", nullable = false)
    private String exchangeCode;

    /**
     * Идентификатор рабочей станции
     */
    @Column(name = "StationId", nullable = true)
    private long stationId;

    /**
     * Код бумаги заявки
     */
    @Column(name = "SecCode", length = 50, nullable = true)
    private String secCode;

    /**
     * Код класса заявки
     */
    @Column(name = "SlassCode", length = 50, nullable = true)
    private String classCode;

    /**
     * Дата и время
     */
    @Column(name = "TradeDateTime", nullable = false)
    private Date tradeDateTime;

    /**
     * Идентификатор расчетного счета/кода в клиринговой организации
     */
    @Column(name = "", nullable = true)
    private String bankAccountId;

    /**
     * Комиссия брокера. Отображается с точностью до 2 двух знаков. Поле зарезервировано для будущего использования
     */
    @Column(name = "BrokerComission", precision = 24, scale = 8, nullable = true)
    private BigDecimal brokerComission;

    /**
     * Номер витринной сделки в Торговой Системе для сделок РЕПО с ЦК и SWAP
     */
    @Column(name = "LinkedTrade", nullable = true)
    private long linkedTrade;

    /**
     * Период торговой сессии. Возможные значения:
     * «0» – Открытие;
     * «1» – Нормальный;
     * «2» – Закрытие
     */
    @Column(name = "Period", nullable = true)
    private short period;

    /**
     * Идентификатор транзакции
     */
    @Column(name = "TransactionId", nullable = true)
    private long transactionId;

    /**
     * Тип сделки. Возможные значения:
     *
     * «1» – Обычная;
     * «2» – Адресная;
     * «3» – Первичное размещение;
     * «4» – Перевод денег/бумаг;
     * «5» – Адресная сделка первой части РЕПО;
     * «6» – Расчетная по операции своп;
     * «7» – Расчетная по внебиржевой операции своп;
     * «8» – Расчетная сделка бивалютной корзины;
     * «9» – Расчетная внебиржевая сделка бивалютной корзины;
     * «10» – Сделка по операции РЕПО с ЦК;
     * «11» – Первая часть сделки по операции РЕПО с ЦК;
     * «12» – Вторая часть сделки по операции РЕПО с ЦК;
     * «13» – Адресная сделка по операции РЕПО с ЦК;
     * «14» – Первая часть адресной сделки по операции РЕПО с ЦК;
     * «15» – Вторая часть адресной сделки по операции РЕПО с ЦК;
     * «16» – Техническая сделка по возврату активов РЕПО с ЦК;
     * «17» – Сделка по спреду между фьючерсами разных сроков на один актив;
     * «18» – Техническая сделка первой части от спреда между фьючерсами;
     * «19» – Техническая сделка второй части от спреда между фьючерсами;
     * «20» – Адресная сделка первой части РЕПО с корзиной;
     * «21» – Адресная сделка второй части РЕПО с корзиной;
     * «22» – Перенос позиций срочного рынка
     */
    @Column(name = "Kind", nullable = true)
    private short kind;

    /**
     * Идентификатор счета в НКЦ (расчетный код)
     */
    @Column(name = "ClearingBankAccountId", nullable = true)
    private String clearingBankAccountId;

    /**
     * Дата и время снятия сделки
     */
    @Column(name = "CanceledDateTime", nullable = true)
    private Date canceledDateTime;

    /**
     * Идентификатор фирмы - участника клиринга
     */
    @Column(name = "ClearingFirmId", nullable = true)
    private String clearingFirmId;

    /**
     * Дополнительная информация по сделке, передаваемая торговой системой
     */
    @Column(name = "SystemRef", nullable = true)
    private String systemRef;

    /**
     * Идентификатор пользователя
     */
    @Column(name = "Uid", nullable = true)
    private long uid;
}
