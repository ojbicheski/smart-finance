-- Expense Group table
CREATE TABLE IF NOT EXISTS finance.tb_expense_group
(
    id          BIGSERIAL    PRIMARY KEY,
    reference   UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER      NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    active      BOOLEAN      NOT NULL,
    customer_id BIGINT       NOT NULL,
    CONSTRAINT fk_customer_id
      FOREIGN KEY(customer_id)
        REFERENCES customer.tb_customer(id)
);

ALTER TABLE finance.tb_expense_group
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_expense_group
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_expense_group_reference
    ON finance.tb_expense_group (reference);
CREATE INDEX idx_customer_id
    ON finance.tb_expense_group (customer_id);

-- Expense Type table
CREATE TABLE IF NOT EXISTS finance.tb_expense_type
(
    id               BIGSERIAL     PRIMARY KEY,
    reference        UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version          INTEGER      NOT NULL,
    created          TIMESTAMP    NOT NULL,
    updated          TIMESTAMP    NOT NULL,
    name             VARCHAR(100) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    active           BOOLEAN      NOT NULL,
    expense_group_id BIGINT       NOT NULL,
    CONSTRAINT fk_expense_group_id
      FOREIGN KEY(expense_group_id)
        REFERENCES finance.tb_expense_group(id)
);

ALTER TABLE finance.tb_expense_type
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_expense_type
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_expense_type_reference
    ON finance.tb_expense_type (reference);
CREATE INDEX idx_tb_expense_type_expense_group_id
    ON finance.tb_expense_type (expense_group_id);

-- Expense table
CREATE TABLE IF NOT EXISTS finance.tb_expense
(
    id              BIGSERIAL   PRIMARY KEY,
    reference       UUID        NOT NULL DEFAULT public.uuid_generate_v4(),
    version         INTEGER     NOT NULL,
    created         TIMESTAMP   NOT NULL,
    updated         TIMESTAMP   NOT NULL,
    expense_date    TIMESTAMP   NOT NULL,
    status          VARCHAR(50) NOT NULL,
    expense_month   INTEGER     NOT NULL,
    expense_year    INTEGER     NOT NULL,
    transact_id     BIGINT      NOT NULL,
    customer_id     BIGINT      NOT NULL,
    expense_type_id BIGINT          NULL,
    CONSTRAINT fk_expense_transact_id
      FOREIGN KEY(transact_id)
        REFERENCES transact.tb_transact(id),
    CONSTRAINT fk_expense_customer_id
      FOREIGN KEY(customer_id)
        REFERENCES customer.tb_customer(id),
    CONSTRAINT fk_expense_type_id
      FOREIGN KEY(expense_type_id)
        REFERENCES finance.tb_expense_type(id)
);

ALTER TABLE finance.tb_expense
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_expense
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_expense_reference
    ON finance.tb_expense (reference);
CREATE UNIQUE INDEX u_tb_expense_customer_type_year_month
    ON finance.tb_expense (customer_id, expense_type_id, expense_year, expense_month);
CREATE INDEX idx_tb_expense_customer_year
    ON finance.tb_expense (customer_id, expense_year);
CREATE INDEX idx_tb_expense_customer_year_month
    ON finance.tb_expense (customer_id, expense_year, expense_month);
CREATE INDEX idx_tb_expense_customer_id
    ON finance.tb_expense (customer_id);

-- Expense Value table
CREATE TABLE IF NOT EXISTS finance.tb_expense_value
(
    id          BIGSERIAL     PRIMARY KEY,
    created     TIMESTAMP     NOT NULL,
    updated     TIMESTAMP     NOT NULL,
    amount      NUMERIC(15,2) NOT NULL,
    expense_id  BIGINT        NOT NULL,
    currency_id BIGINT        NOT NULL,
    exchange_id BIGINT        NULL,
    CONSTRAINT fk_expense_value_expense_id
      FOREIGN KEY(expense_id)
        REFERENCES finance.tb_expense(id),
    CONSTRAINT fk_expense_value_currency_id
      FOREIGN KEY(currency_id)
        REFERENCES currency.tb_currency(id),
    CONSTRAINT fk_expense_value_exchange_id
      FOREIGN KEY(exchange_id)
        REFERENCES currency.tb_exchange(id)
);

ALTER TABLE finance.tb_expense_value
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_expense_value
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_expense_value_expense_currency
    ON finance.tb_expense_value (expense_id, currency_id);

-- Income Type table
CREATE TABLE IF NOT EXISTS finance.tb_income_type
(
    id               BIGSERIAL     PRIMARY KEY,
    reference        UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version          INTEGER      NOT NULL,
    created          TIMESTAMP    NOT NULL,
    updated          TIMESTAMP    NOT NULL,
    name             VARCHAR(100) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    active           BOOLEAN      NOT NULL,
    customer_id     BIGINT      NOT NULL,
    CONSTRAINT fk_income_type_customer_id
      FOREIGN KEY(customer_id)
        REFERENCES customer.tb_customer(id)
);

ALTER TABLE finance.tb_income_type
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_income_type
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_income_type_reference
    ON finance.tb_income_type (reference);
CREATE INDEX idx_tb_income_type_customer_id
    ON finance.tb_income_type (customer_id);

-- Expense table
CREATE TABLE IF NOT EXISTS finance.tb_income
(
    id             BIGSERIAL   PRIMARY KEY,
    reference      UUID        NOT NULL DEFAULT public.uuid_generate_v4(),
    version        INTEGER     NOT NULL,
    created        TIMESTAMP   NOT NULL,
    updated        TIMESTAMP   NOT NULL,
    income_date    TIMESTAMP   NOT NULL,
    status         VARCHAR(50) NOT NULL,
    income_month   INTEGER     NOT NULL,
    income_year    INTEGER     NOT NULL,
    transact_id    BIGINT      NOT NULL,
    customer_id    BIGINT      NOT NULL,
    income_type_id BIGINT          NULL,
    CONSTRAINT fk_income_transact_id
      FOREIGN KEY(transact_id)
        REFERENCES transact.tb_transact(id),
    CONSTRAINT fk_income_customer_id
      FOREIGN KEY(customer_id)
        REFERENCES customer.tb_customer(id),
    CONSTRAINT fk_income_type_id
      FOREIGN KEY(income_type_id)
        REFERENCES finance.tb_income_type(id)
);

ALTER TABLE finance.tb_income
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_income
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_income_reference
    ON finance.tb_income (reference);
CREATE UNIQUE INDEX u_tb_income_customer_type_year_month
    ON finance.tb_income (customer_id, income_type_id, income_year, income_month);
CREATE INDEX idx_tb_income_customer_year
    ON finance.tb_income (customer_id, income_year);
CREATE INDEX idx_tb_income_customer_year_month
    ON finance.tb_income (customer_id, income_year, income_month);
CREATE INDEX idx_tb_income_customer_id
    ON finance.tb_income (customer_id);

-- Expense Value table
CREATE TABLE IF NOT EXISTS finance.tb_income_value
(
    id          BIGSERIAL     PRIMARY KEY,
    created     TIMESTAMP     NOT NULL,
    updated     TIMESTAMP     NOT NULL,
    amount      NUMERIC(15,2) NOT NULL,
    income_id   BIGINT        NOT NULL,
    currency_id BIGINT        NOT NULL,
    exchange_id BIGINT        NULL,
    CONSTRAINT fk_income_value_income_id
      FOREIGN KEY(income_id)
        REFERENCES finance.tb_income(id),
    CONSTRAINT fk_income_value_currency_id
      FOREIGN KEY(currency_id)
        REFERENCES currency.tb_currency(id),
    CONSTRAINT fk_income_value_exchange_id
      FOREIGN KEY(exchange_id)
        REFERENCES currency.tb_exchange(id)
);

ALTER TABLE finance.tb_income_value
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE finance.tb_income_value
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_income_value_income_currency
    ON finance.tb_income_value (income_id, currency_id);
