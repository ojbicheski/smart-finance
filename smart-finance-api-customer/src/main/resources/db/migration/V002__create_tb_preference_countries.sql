-- Customer table
CREATE TABLE IF NOT EXISTS customer.tb_customer
(
    id        BIGSERIAL    PRIMARY KEY,
    reference UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version   INTEGER      NOT NULL,
    created   TIMESTAMP    NOT NULL,
    updated   TIMESTAMP    NOT NULL,
    name      VARCHAR(255) NOT NULL,
    active    BOOLEAN      NOT NULL
);

ALTER TABLE customer.tb_customer
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE customer.tb_customer
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_customer_reference ON customer.tb_customer (reference);

-- Account Type table
CREATE TABLE IF NOT EXISTS customer.tb_account_type
(
    id          BIGSERIAL    PRIMARY KEY,
    reference   UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER      NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    active      BOOLEAN      NOT NULL,
    description VARCHAR(255) NOT NULL
);

ALTER TABLE customer.tb_account_type
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE customer.tb_account_type
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_account_type_reference ON customer.tb_account_type (reference);

-- Account table
CREATE TABLE IF NOT EXISTS customer.tb_account
(
    id                BIGSERIAL    PRIMARY KEY,
    reference         UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version           INTEGER      NOT NULL,
    created           TIMESTAMP    NOT NULL,
    updated           TIMESTAMP    NOT NULL,
    number_acct       VARCHAR(50)  NOT NULL,
    description       VARCHAR(255) NOT NULL,
    active            BOOLEAN      NOT NULL,
    customer_id       BIGINT       NOT NULL,
    account_type_id   BIGINT       NOT NULL,
    operator_id       BIGINT       NOT NULL,
    product_detail_id BIGINT       NOT NULL,
    currency_id       BIGINT       NOT NULL,
    CONSTRAINT fk_customer_id
      FOREIGN KEY(customer_id)
        REFERENCES customer.tb_customer(id),
    CONSTRAINT fk_account_type_id
      FOREIGN KEY(account_type_id)
        REFERENCES customer.tb_account_type(id),
    CONSTRAINT fk_tb_operator_id
      FOREIGN KEY(operator_id)
        REFERENCES financial_operator.tb_operator(id),
    CONSTRAINT fk_tb_product_detail_id
      FOREIGN KEY(product_detail_id)
        REFERENCES financial_operator.tb_product_detail(id),
    CONSTRAINT fk_tb_currency_id
      FOREIGN KEY(currency_id)
        REFERENCES currency.tb_currency(id)
);

ALTER TABLE customer.tb_account
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE customer.tb_account
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_account_reference ON customer.tb_account (reference);
CREATE UNIQUE INDEX u_tb_account_number_acct ON customer.tb_account (customer_id, number_acct);


-- Account_Template table
CREATE TABLE IF NOT EXISTS customer.tb_account_template
(
    id          BIGSERIAL PRIMARY KEY,
    reference   UUID      NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER   NOT NULL,
    created     TIMESTAMP NOT NULL,
    updated     TIMESTAMP NOT NULL,
    active      BOOLEAN   NOT NULL,
    account_id  BIGINT    NOT NULL,
    template_id BIGINT    NOT NULL,
    CONSTRAINT fk_account_id
      FOREIGN KEY(account_id)
        REFERENCES customer.tb_account(id),
    CONSTRAINT fk_tb_template_id
      FOREIGN KEY(template_id)
        REFERENCES financial_operator.tb_template_file(id)
);

ALTER TABLE customer.tb_account_template
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE customer.tb_account_template
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_account_template_reference ON customer.tb_account_template (reference);