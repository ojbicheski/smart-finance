-- Transact table
CREATE TABLE IF NOT EXISTS transact.tb_transact
(
    id             BIGSERIAL     PRIMARY KEY,
    reference      UUID          NOT NULL DEFAULT public.uuid_generate_v4(),
    version        INTEGER       NOT NULL,
    created        TIMESTAMP     NOT NULL,
    updated        TIMESTAMP     NOT NULL,
    transact_date  TIMESTAMP     NOT NULL,
    name           VARCHAR(100)  NOT NULL,
    description    VARCHAR(255)  NOT NULL,
    type           VARCHAR(10)   NOT NULL,
    transact_month INTEGER       NOT NULL,
    transact_year  INTEGER       NOT NULL,
    amount         NUMERIC(15,2) NOT NULL,
    account_id     BIGINT        NOT NULL,
    currency_id    BIGINT        NOT NULL,
    CONSTRAINT fk_transact_account_id
      FOREIGN KEY(account_id)
        REFERENCES customer.tb_account(id),
    CONSTRAINT fk_transact_currency_id_id
      FOREIGN KEY(currency_id)
        REFERENCES currency.tb_currency(id)
);

ALTER TABLE transact.tb_transact
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE transact.tb_transact
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_transact_reference
    ON transact.tb_transact (reference);
CREATE INDEX idx_tb_transact_account_year
    ON transact.tb_transact (account_id, transact_year);
CREATE INDEX idx_tb_transact_account_year_month
    ON transact.tb_transact (account_id, transact_year, transact_month);

-- Account Balance table
CREATE TABLE IF NOT EXISTS transact.tb_account_balance
(
    id            BIGSERIAL     PRIMARY KEY,
    reference     UUID          NOT NULL DEFAULT public.uuid_generate_v4(),
    version       INTEGER       NOT NULL,
    created       TIMESTAMP     NOT NULL,
    updated       TIMESTAMP     NOT NULL,
    balance_month INTEGER       NOT NULL,
    balance_year  INTEGER       NOT NULL,
    status        VARCHAR(50)   NOT NULL,
    amount        NUMERIC(15,2) NOT NULL,
    account_id    BIGINT        NOT NULL,
    currency_id   BIGINT        NOT NULL,
    CONSTRAINT fk_account_balance_account_id
      FOREIGN KEY(account_id)
        REFERENCES customer.tb_account(id),
    CONSTRAINT fk_account_balance_currency_id
      FOREIGN KEY(currency_id)
        REFERENCES currency.tb_currency(id)
);

ALTER TABLE transact.tb_account_balance
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE transact.tb_account_balance
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_account_balance_reference
    ON transact.tb_account_balance (reference);
CREATE UNIQUE INDEX u_tb_account_balance_account_year_month
    ON transact.tb_account_balance (account_id, balance_year, balance_month);
