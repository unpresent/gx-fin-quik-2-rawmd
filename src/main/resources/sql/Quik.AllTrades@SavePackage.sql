CREATE OR REPLACE PROCEDURE "Quik"."AllTrades@SavePackage"(Data text)
LANGUAGE PLPGSQL
AS $$
BEGIN
    INSERT INTO "Quik"."AllTrades"
    (
        "ExchangeCode",
        "TradeNum",
        "Direction",
        "TradeDateTime",
        "ClassCode",
        "SecCode",
        "Price",
        "Quantity",
        "Value",
        "AccruedInterest",
        "Yield",
        "SettleCode",
        "RepoRate",
        "RepoValue",
        "Repo2Value",
        "RepoTerm",
        "Period",
        "OpenInterest"
	)
    SELECT D.*
	FROM JSONB_TO_RECORDSET(JSONB_EXTRACT_PATH(Data :: JSONB, '$.objects')) AS D (
        "exchangeCode"      character varying(50),
        "tradeNum"          character varying(50),
        "direction"         character varying(10),
        "tradeDateTime"     timestamp without time zone,
        "classCode"         character varying(50),
        "secCode"           character varying(50),
        "price"             numeric(24,8),
        "quantity"          numeric(24,8),
        "value"             numeric(24,8),
        "accruedInterest"   numeric(24,8),
        "yield"             numeric(24,8),
        "settleCode"        character varying(50),
        "repoRate"          numeric(24,8),
        "repoValue"         numeric(24,8),
        "repo2Value"        numeric(24,8),
        "repoTerm"          integer,
        "period"            smallint,
        "openInterest"      integer
   )
    ON CONFLICT ("ExchangeCode", "TradeNum") DO UPDATE SET
        "Direction"         = EXCLUDED."Direction",
        "TradeDateTime"     = EXCLUDED."TradeDateTime",
        "ClassCode"         = EXCLUDED."ClassCode",
        "SecCode"           = EXCLUDED."SecCode",
        "Price"             = EXCLUDED."Price",
        "Quantity"          = EXCLUDED."Quantity",
        "Value"             = EXCLUDED."Value",
        "AccruedInterest"   = EXCLUDED."AccruedInterest",
        "Yield"             = EXCLUDED."Yield",
        "SettleCode"        = EXCLUDED."SettleCode",
        "RepoRate"          = EXCLUDED."RepoRate",
        "RepoValue"         = EXCLUDED."RepoValue",
        "Repo2Value"        = EXCLUDED."Repo2Value",
        "RepoTerm"          = EXCLUDED."RepoTerm",
        "Period"            = EXCLUDED."Period",
        "OpenInterest"      = EXCLUDED."OpenInterest";
END;
$$;