ALTER TABLE BANK_SERVICE_SCHEMA.ACCOUNTS
    ADD CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_ID) REFERENCES BANK_SERVICE_SCHEMA.CLIENTS (ID);

ALTER TABLE BANK_SERVICE_SCHEMA.TRANSACTIONS
    ADD CONSTRAINT FK_SENDER_ACCOUNT FOREIGN KEY (SENDER_ACCOUNT_ID) REFERENCES BANK_SERVICE_SCHEMA.ACCOUNTS (ID);

ALTER TABLE BANK_SERVICE_SCHEMA.TRANSACTIONS
    ADD CONSTRAINT FK_RECEIVER_ACCOUNT FOREIGN KEY (RECEIVER_ACCOUNT_ID) REFERENCES BANK_SERVICE_SCHEMA.ACCOUNTS (ID);
