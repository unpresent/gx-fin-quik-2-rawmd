-- Table: Quik.Orders
-- DROP TABLE "Quik"."Orders";

CREATE TABLE IF NOT EXISTS "Quik"."Orders"
(
    "ExchangeCode"      character varying(255) COLLATE pg_catalog."default"     NOT NULL,
    "OrderNum"          character varying(50) COLLATE pg_catalog."default"      NOT NULL,
    "Account"           character varying(50) COLLATE pg_catalog."default"          NULL,
    "AccountType"       integer                                                     NULL,
    "AccruedInterest"   numeric(24,8)                                               NULL,
    "ActivationTime"    bigint                                                      NULL,
    "Balance"           numeric(24,8)                                           NOT NULL,
    "BankAccountId"     character varying(50) COLLATE pg_catalog."default"          NULL,
    "BrokerRef"         character varying(50) COLLATE pg_catalog."default"          NULL,
    "CanceledUid"       bigint                                                      NULL,
    "Capacity"          numeric(24,8)                                               NULL,
    "ClassCode"         character varying(50) COLLATE pg_catalog."default"          NULL,
    "ClientCode"        character varying(50) COLLATE pg_catalog."default"          NULL,
    "Direction"         character varying(50) COLLATE pg_catalog."default"          NULL,
    "ExecType"          integer                                                     NULL,
    "Expiry"            bigint                                                      NULL,
    "ExtOrderFlags"     integer                                                     NULL,
    "FirmId"            character varying(50) COLLATE pg_catalog."default"          NULL,
    "LinkedOrder"       bigint                                                      NULL,
    "MinQuantity"       numeric(24,8)                                               NULL,
    "PassiveOnlyOrder"  smallint                                                    NULL,
    "Price"             numeric(24,8)                                               NULL,
    "Price2"            numeric(24,8)                                               NULL,
    "Quantity"          numeric(24,8)                                           NOT NULL,
    "RejectReason"      character varying(50) COLLATE pg_catalog."default"          NULL,
    "Repo2Value"        numeric(24,8)                                               NULL,
    "RepoTerm"          integer                                                     NULL,
    "RepoValue"         numeric(24,8)                                               NULL,
    "repoValueBalance"  numeric(24,8)                                               NULL,
    "SecCode"           character varying(50) COLLATE pg_catalog."default"          NULL,
    "SettleCode"        character varying(50) COLLATE pg_catalog."default"          NULL,
    "SideQualifier"     integer                                                     NULL,
    "StartDiscount"     numeric(24,8)                                               NULL,
    "TradeDateTime"     timestamp without time zone                             NOT NULL,
    "TransactionId"     bigint                                                      NULL,
    "Uid"               bigint                                                      NULL,
    "UserId"            character varying(50) COLLATE pg_catalog."default"          NULL,
    "Value"             numeric(24,8)                                           NOT NULL,
    "ValueEntryType"    smallint                                                    NULL,
    "Visible"           numeric(24,8)                                               NULL,
    "WithdrawDateTime"  timestamp without time zone                             NOT NULL,
    "Yield"             numeric(24,8),

    CONSTRAINT "Orders_pkey" PRIMARY KEY ("ExchangeCode", "OrderNum")
) TABLESPACE pg_default;

ALTER TABLE "Quik"."Orders" OWNER TO quik;