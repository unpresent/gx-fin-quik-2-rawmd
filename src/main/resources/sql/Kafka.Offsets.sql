-- Table: Kafka.Offsets
-- DROP TABLE "Kafka"."Offsets";

CREATE TABLE IF NOT EXISTS "Kafka"."Offsets"
(
    "Reader"            character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Topic"             character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Partition"         integer                                             NOT NULL,
    "Offset"            bigint                                              NOT NULL,
    CONSTRAINT "Offsets_pkey" PRIMARY KEY ("Reader", "Topic", "Partition", "Offset")
) TABLESPACE pg_default;

ALTER TABLE "Kafka"."Offsets" OWNER TO postgres;
