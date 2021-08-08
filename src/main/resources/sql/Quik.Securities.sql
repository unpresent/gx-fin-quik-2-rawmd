-- Table: Quik.Securities
-- DROP TABLE "Quik"."Securities";

CREATE TABLE IF NOT EXISTS "Quik"."Securities"
(
    "Code"          character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    "ClassCode"     character varying(20) COLLATE pg_catalog."default"  NOT NULL,
    "ClassName"     character varying(100) COLLATE pg_catalog."default"     NULL,
    "FaceUnit"      character varying(3) COLLATE pg_catalog."default"       NULL,
    "FaceValue"     numeric(24,8)                                           NULL,
    "IsinCode"      character varying(20) COLLATE pg_catalog."default"      NULL,
    "LotSize"       numeric(24,8)                                           NULL,
    "MaturityDate"  bigint                                                  NULL,
    "MinPriceStep"  numeric(24,8)                                           NULL,
    "Name"          character varying(250) COLLATE pg_catalog."default"     NULL,
    "Scale"         integer                                                 NULL,
    "ShortName"     character varying(100) COLLATE pg_catalog."default"     NULL,

    CONSTRAINT "Securities_pkey" PRIMARY KEY ("Code", "ClassCode")
) TABLESPACE pg_default;

ALTER TABLE "Quik"."Securities" OWNER TO quik;