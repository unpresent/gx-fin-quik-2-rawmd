-- Table: Quik.AllTrades
-- DROP TABLE "Quik"."AllTrades";

CREATE TABLE IF NOT EXISTS "Quik"."AllTrades"
(
    "ExchangeCode"      character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    "TradeNum"          character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    "Direction"         character varying(10)                                   NULL,
    "TradeDateTime"     timestamp without time zone                         NOT NULL,
    "ClassCode"         character varying(50) COLLATE pg_catalog."default"      NULL,
    "SecCode"           character varying(50) COLLATE pg_catalog."default"      NULL,
    "Price"             numeric(24,8)                                           NULL,
    "Quantity"          numeric(24,8)                                           NULL,
    "Value"             numeric(24,8)                                           NULL,
    "AccruedInterest"   numeric(24,8)                                           NULL,
    "Yield"             numeric(24,8)                                           NULL,
    "SettleCode"        character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    "RepoRate"          numeric(24,8)                                           NULL,
    "RepoValue"         numeric(24,8)                                           NULL,
    "Repo2Value"        numeric(24,8)                                           NULL,
    "RepoTerm"          integer                                                 NULL,
    "Period"            smallint                                                NULL,
    "OpenInterest"      integer                                                 NULL,

    CONSTRAINT "AllTrades_pkey" PRIMARY KEY ("ExchangeCode", "TradeNum")
) TABLESPACE pg_default;

ALTER TABLE "Quik"."AllTrades" OWNER TO quik;
