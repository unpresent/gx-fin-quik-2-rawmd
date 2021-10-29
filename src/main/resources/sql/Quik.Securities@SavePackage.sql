CREATE OR REPLACE PROCEDURE "Quik"."Securities@SavePackage"(Data character varying)
    LANGUAGE PLPGSQL
AS $$
BEGIN
    INSERT INTO "Quik"."Securities"
    (
        "Code",
        "ClassCode",
        "ClassName",
        "FaceUnit",
        "FaceValue",
        "IsinCode",
        "LotSize",
        "MaturityDate",
        "MinPriceStep",
        "Name",
        "Scale",
        "ShortName"
	)
    SELECT D.*
    FROM JSONB_TO_RECORDSET(JSONB_EXTRACT_PATH(Data :: JSONB, '$.objects')) AS D
    (
        "code"          character varying(50),
        "classCode"     character varying(20),
        "className"     character varying(100),
        "faceUnit"      character varying(3),
        "faceValue"     numeric(24,8),
        "isinCode"      character varying(20),
        "lotSize"       numeric(24,8),
        "maturityDate"  bigint,
        "minPriceStep"  numeric(24,8),
        "name"          character varying(250),
        "scale"         integer,
        "shortName"     character varying(100)
    )
    ON CONFLICT ("ClassCode", "Code") DO UPDATE SET
        "ClassName"     = EXCLUDED."ClassName",
        "FaceUnit"      = EXCLUDED."FaceUnit",
        "FaceValue"     = EXCLUDED."FaceValue",
        "IsinCode"      = EXCLUDED."IsinCode",
        "LotSize"       = EXCLUDED."LotSize",
        "MaturityDate"  = EXCLUDED."MaturityDate",
        "MinPriceStep"  = EXCLUDED."MinPriceStep",
        "Name"          = EXCLUDED."Name",
        "Scale"         = EXCLUDED."Scale",
        "ShortName"     = EXCLUDED."ShortName";
END;
$$;