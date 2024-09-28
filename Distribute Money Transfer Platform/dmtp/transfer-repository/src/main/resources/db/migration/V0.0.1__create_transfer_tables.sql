CREATE TABLE DMTP_SCHEMA.TRANSFER
(
    id                       VARCHAR PRIMARY KEY,
    amount                   NUMERIC(8, 2),
    currency                 VARCHAR(3),
    mts                      VARCHAR(30),
    receive_country          VARCHAR(50),
    send_country             VARCHAR(50),

    receiver_address         VARCHAR(200),
    receiver_birth_country   VARCHAR(100),
    receiver_birth_date      TIMESTAMP,
    receiver_citizenship     VARCHAR(60),
    receiver_country         VARCHAR(100),
    receiver_email           VARCHAR(100),
    receiver_first_name      VARCHAR(100),
    receiver_last_name       VARCHAR(100),
    receiver_middle_name     VARCHAR(100),
    receiver_mobile_phone    VARCHAR(100),
    receiver_personal_number VARCHAR(100),
    receiver_zip_code        VARCHAR(60),

    sender_address           VARCHAR(200),
    sender_birth_country     VARCHAR(100),
    sender_birth_date        TIMESTAMP,
    sender_citizenship       VARCHAR(60),
    sender_country           VARCHAR(100),
    sender_email             VARCHAR(100),
    sender_first_name        VARCHAR(100),
    sender_last_name         VARCHAR(100),
    sender_middle_name       VARCHAR(100),
    sender_mobile_phone      VARCHAR(100),
    sender_personal_number   VARCHAR(100),
    sender_zip_code          VARCHAR(60),

    status                   VARCHAR(100) NOT NULL,
    status_message           VARCHAR(500),
    status_time              TIMESTAMP    NOT NULL,
    transaction_time         TIMESTAMP    NOT NULL,
    transfer_number          VARCHAR(50),
    type                     VARCHAR(10)
);

CREATE TABLE DMTP_SCHEMA.TRANSFER_STATUS_LOG
(
    id             BIGSERIAL PRIMARY KEY,
    status         VARCHAR(100) NOT NULL,
    status_message VARCHAR(500),
    status_time    TIMESTAMP    NOT NULL,
    username       VARCHAR(100),
    transfer_id    VARCHAR REFERENCES DMTP_SCHEMA.transfer (id)
);
