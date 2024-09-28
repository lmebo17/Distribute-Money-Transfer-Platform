CREATE TABLE IF NOT EXISTS BANK_SERVICE_SCHEMA.LOG_ENTRIES
(
    ID               BIGINT DEFAULT NEXTVAL('BANK_SERVICE_SCHEMA.BANK_SERVICE_GLOBAL_SEQUENCE') PRIMARY KEY,
    USERNAME         VARCHAR(50) NOT NULL,
    METHOD_NAME      VARCHAR(50) NOT NULL,
    METHOD_ARGUMENTS VARCHAR     NOT NULL,
    CALL_TIME        TIMESTAMP   NOT NULL
);