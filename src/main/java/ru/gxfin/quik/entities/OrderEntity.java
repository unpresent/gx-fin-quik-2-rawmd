package ru.gxfin.quik.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.gxfin.common.data.AbstractEntityObject;
import ru.gxfin.gate.quik.model.internal.QuikDealDirection;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сделка
 */
@Entity
@IdClass(OrderEntityId.class)
@Table(schema = "Quik", name = "Orders")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class OrderEntity extends AbstractEntityObject {
    /**
     * Код биржи в торговой системе
     */
    @Id
    @Column(name = "ExchangeCode", nullable = false)
    private String exchangeCode;

    /**
     * Номер заявки в торговой системе
     */
    @Id
    @Column(name = "OrderNum", length = 50, nullable = false)
    private String orderNum;

    /**
     * Направление поручения
     */
    @Column(name = "Direction")
    private QuikDealDirection direction;

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
     * Количество в лотах
     */
    @Column(name = "Balance", precision = 24, scale = 8, nullable = false)
    private BigDecimal balance;

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
     * Идентификатор транзакции
     */
    @Column(name = "TransactionId")
    private long transactionId;

    /**
     * Код клиента
     */
    @Column(name = "ClientCode", length = 50)
    private String clientCode;

    /**
     * Цена выкупа
     */
    @Column(name = "Price2", precision = 24, scale = 8)
    private BigDecimal price2;

    /**
     * Код расчетов
     */
    @Column(name = "SettleCode", length = 50)
    private String settleCode;

    /**
     * Идентификатор пользователя
     */
    @Column(name = "Uid")
    private long uid;

    /**
     * Идентификатор пользователя, снявшего заявку
     */
    @Column(name = "CanceledUid")
    private long canceledUid;

    /**
     * Время активации
     */
    @Column(name = "ActivationTime")
    private LocalDateTime activationTime;

    /**
     * Номер заявки в торговой системе
     */
    @Column(name = "LinkedOrder")
    private long linkedOrder;

    /**
     * Дата окончания срока действия заявки
     */
    @Column(name = "Expiry")
    private long expiry;

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
     * Дата и время снятия заявки
     */
    @Column(name = "WithdrawDateTime", nullable = false)
    private LocalDateTime withdrawDateTime;

    /**
     * Идентификатор расчетного счета/кода в клиринговой организации
     */
    @Column(name = "BankAccountId", length = 50)
    private String bankAccountId;

    /**
     * Способ указания объема заявки. Возможные значения:
     * «0» – по количеству,
     * «1» – по объему
     */
    @Column(name = "ValueEntryType")
    private byte valueEntryType;

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
     * Остаток суммы РЕПО за вычетом суммы привлеченных
     * или предоставленных по сделке РЕПО денежных средств в неисполненной части заявки,
     * по состоянию на текущую дату.
     * Отображается с точностью 2 знака
     */
    @Column(name = "repoValueBalance", precision = 24, scale = 8)
    private BigDecimal repoValueBalance;

    /**
     * Начальный дисконт (%)
     */
    @Column(name = "StartDiscount", precision = 24, scale = 8)
    private BigDecimal startDiscount;

    /**
     * Причина отклонения заявки брокером
     */
    @Column(name = "RejectReason", length = 50)
    private String rejectReason;

    /**
     * Битовое поле для получения специфических параметров с западных площадок
     */
    @Column(name = "ExtOrderFlags")
    private int extOrderFlags;

    /**
     * Минимально допустимое количество, которое можно указать в заявке по данному инструменту.
     * Если имеет значение «0», значит ограничение по количеству не задано
     */
    @Column(name = "MinQuantity", precision = 24, scale = 8)
    private BigDecimal minQuantity;

    /**
     * Тип исполнения заявки. Если имеет значение «0», значит значение не задано
     */
    @Column(name = "ExecType")
    private int execType;

    /**
     * Поле для получения параметров по западным площадкам. Если имеет значение «0», значит значение не задано
     */
    @Column(name = "SideQualifier")
    private int sideQualifier;

    /**
     * Поле для получения параметров по западным площадкам. Если имеет значение «0», значит значение не задано
     */
    @Column(name = "AccountType")
    private int accountType;

    /**
     * Поле для получения параметров по западным площадкам. Если имеет значение «0», значит значение не задано
     */
    @Column(name = "Capacity", precision = 24, scale = 8)
    private BigDecimal capacity;

    /**
     * Поле для получения параметров по западным площадкам. Если имеет значение «0», значит значение не задано
     */
    @Column(name = "PassiveOnlyOrder", precision = 24, scale = 8)
    private byte passiveOnlyOrder;

    /**
     * Видимое количество. Параметр айсберг-заявок, для обычных заявок выводится значение: «0»
     */
    @Column(name = "Visible", precision = 24, scale = 8)
    private BigDecimal visible;

    @SuppressWarnings("unused")
    public OrderEntity() {
        super();
    }
}
