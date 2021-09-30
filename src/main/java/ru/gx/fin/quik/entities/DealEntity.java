package ru.gx.fin.quik.entities;

import lombok.*;
import lombok.experimental.Accessors;
import ru.gx.data.jpa.AbstractEntityObject;
import ru.gx.fin.gate.quik.model.internal.QuikDealDirection;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PUBLIC;

/**
 * Сделка
 */
@Entity
@IdClass(DealEntityId.class)
@Table(schema = "Quik", name = "Deals")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor(access = PUBLIC)
public class DealEntity extends AbstractEntityObject {
    /**
     * Код биржи в торговой системе
     */
    @Id
    @Column(name = "ExchangeCode", nullable = false)
    private String exchangeCode;

    /**
     * Номер сделки в торговой системе
     */
    @Id
    @Column(name = "TradeNum", length = 50, nullable = false)
    private String tradeNum;

    /**
     * Номер заявки в торговой системе
     */
    @Column(name = "OrderNum", length = 50)
    private String orderNum;

    /**
     * Комментарий, обычно: <код клиента>/<номер поручения>
     */
    @Column(name = "BrokerRef", length = 50)
    private String brokerRef;

    /**
     * Идентификатор трейдера
     */
    @Column(name = "UserId", length = 50)
    private String userId;

    /**
     * Идентификатор фирмы
     */
    @Column(name = "FirmId", length = 50)
    private String firmId;

    /**
     * Идентификатор пользователя, снявшего заявку
     */
    @Column(name = "CanceledUid")
    private long canceledUid;

    /**
     * Торговый счет
     */
    @Column(name = "Account", length = 50)
    private String account;

    /**
     * Цена
     */
    @Column(name = "Price", precision = 24, scale = 8)
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
    @Column(name = "SettleCode", length = 50)
    private String settleCode;

    /**
     * Код фирмы партнера
     */
    @Column(name = "CpFirmId", length = 50)
    private String cpFirmId;

    /**
     * Направление сделки
     */
    @Column(name = "Direction")
    private QuikDealDirection direction;

    /**
     * Цена выкупа
     */
    @Column(name = "Price2", precision = 24, scale = 8)
    private BigDecimal price2;

    /**
     * Ставка РЕПО (%)
     */
    @Column(name = "RepoRate", precision = 24, scale = 8)
    private BigDecimal repoRate;

    /**
     * Код клиента
     */
    @Column(name = "ClientCode", length = 50)
    private String clientCode;

    /**
     * Доход (%) на дату выкупа
     */
    @Column(name = "Accrued2", precision = 24, scale = 8)
    private BigDecimal accrued2;

    /**
     * Срок РЕПО, в календарных днях
     */
    @Column(name = "RepoTerm")
    private int repoTerm;

    /**
     * Сумма РЕПО на текущую дату. Отображается с точностью 2 знака
     */
    @Column(name = "RepoValue", precision = 24, scale = 8)
    private BigDecimal repoValue;

    /**
     * Объём сделки выкупа РЕПО. Отображается с точностью 2 знака
     */
    @Column(name = "Repo2Value", precision = 24, scale = 8)
    private BigDecimal repo2Value;

    /**
     * Начальный дисконт (%)
     */
    @Column(name = "StartDiscount", precision = 24, scale = 8)
    private BigDecimal startDiscount;

    /**
     * Нижний дисконт (%)
     */
    @Column(name = "LowerDiscount", precision = 24, scale = 8)
    private BigDecimal lowerDiscount;

    /**
     * Верхний дисконт (%)
     */
    @Column(name = "UpperDiscount", precision = 24, scale = 8)
    private BigDecimal upperDiscount;

    /**
     * Блокировка обеспечения («Да»/«Нет»)
     */
    @Column(name = "BlockSecurities", precision = 24, scale = 8)
    private BigDecimal blockSecurities;

    /**
     * Клиринговая комиссия (ММВБ)
     */
    @Column(name = "ClearingComission", precision = 24, scale = 8)
    private BigDecimal clearingComission;

    /**
     * Комиссия Фондовой биржи (ММВБ)
     */
    @Column(name = "ExchangeComission", precision = 24, scale = 8)
    private BigDecimal exchangeComission;

    /**
     * Комиссия Технического центра (ММВБ)
     */
    @Column(name = "TechCenterComission", precision = 24, scale = 8)
    private BigDecimal techCenterComission;

    /**
     * Дата расчетов
     */
    @Column(name = "SettleDate")
    private int settleDate;

    /**
     * Валюта расчетов
     */
    @Column(name = "SettleCurrency", length = 10)
    private String settleCurrency;

    /**
     * Валюта
     */
    @Column(name = "TradeCurrency", length = 10)
    private String tradeCurrency;

    /**
     * Идентификатор рабочей станции
     */
    @Column(name = "StationId")
    private long stationId;

    /**
     * Код бумаги заявки
     */
    @Column(name = "SecCode", length = 50)
    private String secCode;

    /**
     * Код класса заявки
     */
    @Column(name = "ClassCode", length = 50)
    private String classCode;

    /**
     * Дата и время
     */
    @Column(name = "TradeDateTime", nullable = false)
    private LocalDateTime tradeDateTime;

    /**
     * Идентификатор расчетного счета/кода в клиринговой организации
     */
    @Column(name = "BankAccountId")
    private String bankAccountId;

    /**
     * Комиссия брокера. Отображается с точностью до 2 двух знаков. Поле зарезервировано для будущего использования
     */
    @Column(name = "BrokerComission", precision = 24, scale = 8)
    private BigDecimal brokerComission;

    /**
     * Номер витринной сделки в Торговой Системе для сделок РЕПО с ЦК и SWAP
     */
    @Column(name = "LinkedTrade")
    private long linkedTrade;

    /**
     * Период торговой сессии. Возможные значения:
     * «0» – Открытие;
     * «1» – Нормальный;
     * «2» – Закрытие
     */
    @Column(name = "Period")
    private short period;

    /**
     * Идентификатор транзакции
     */
    @Column(name = "TransactionId")
    private long transactionId;

    /**
     * Тип сделки. Возможные значения:
     * <p>
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
    @Column(name = "Kind")
    private short kind;

    /**
     * Идентификатор счета в НКЦ (расчетный код)
     */
    @Column(name = "ClearingBankAccountId")
    private String clearingBankAccountId;

    /**
     * Дата и время снятия сделки
     */
    @Column(name = "CanceledDateTime")
    private LocalDateTime canceledDateTime;

    /**
     * Идентификатор фирмы - участника клиринга
     */
    @Column(name = "ClearingFirmId")
    private String clearingFirmId;

    /**
     * Дополнительная информация по сделке, передаваемая торговой системой
     */
    @Column(name = "SystemRef")
    private String systemRef;

    /**
     * Идентификатор пользователя
     */
    @Column(name = "Uid")
    private long uid;
}
