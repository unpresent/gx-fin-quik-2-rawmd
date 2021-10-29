-- Table: Kafka.Offsets
-- DROP TABLE "Kafka"."Offsets";

CREATE TABLE IF NOT EXISTS "Kafka"."Offsets"
(
    "Direction"         character varying(10)  COLLATE pg_catalog."default" NOT NULL,
    "ServiceName"       character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Topic"             character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Partition"         integer                                             NOT NULL,
    "Offset"            bigint                                                  NULL,
    CONSTRAINT "IncomeOffsets_pkey" PRIMARY KEY ("Direction", "ServiceName", "Topic", "Partition")
) TABLESPACE pg_default;

ALTER TABLE "Kafka"."Offsets" OWNER TO gxfin;
