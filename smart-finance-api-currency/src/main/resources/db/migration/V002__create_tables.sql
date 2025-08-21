-- Currency table
CREATE TABLE IF NOT EXISTS currency.tb_currency
(
    id          BIGSERIAL    PRIMARY KEY,
    reference   UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER      NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    code        VARCHAR(3)   NOT NULL,
    symbol      VARCHAR(255) NOT NULL,
    country_id  BIGSERIAL    NOT NULL,
    active      BOOLEAN      NOT NULL,
    CONSTRAINT fk_country_id
      FOREIGN KEY(country_id)
        REFERENCES shared_data.tb_country(id)
);

ALTER TABLE currency.tb_currency
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE currency.tb_currency
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';

CREATE UNIQUE INDEX uk_currency_reference ON currency.tb_currency (reference);
CREATE UNIQUE INDEX uk_currency_code ON currency.tb_currency (code);

-- Exchange table
CREATE TABLE IF NOT EXISTS currency.tb_exchange
(
    id             BIGSERIAL        PRIMARY KEY,
    reference      UUID             NOT NULL DEFAULT public.uuid_generate_v4(),
    version        INTEGER          NOT NULL,
    created        TIMESTAMP        NOT NULL,
    updated        TIMESTAMP        NOT NULL,
    exchanged      TIMESTAMP        NOT NULL,
    value          DOUBLE PRECISION NOT NULL,
    currency_from  BIGSERIAL        NOT NULL,
    currency_to    BIGSERIAL        NOT NULL,
    CONSTRAINT fk_currency_from
      FOREIGN KEY(currency_from)
        REFERENCES currency.tb_currency(id),
    CONSTRAINT fk_currency_to
      FOREIGN KEY(currency_to)
        REFERENCES currency.tb_currency(id)
);

ALTER TABLE currency.tb_exchange
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE currency.tb_exchange
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';

CREATE UNIQUE INDEX uk_exchange_reference ON currency.tb_exchange (reference);
CREATE UNIQUE INDEX uk_exchange_dt_curfrom_curto ON currency.tb_exchange (exchanged, currency_from, currency_to);
CREATE INDEX idx_exchange_dt_curfrom ON currency.tb_exchange (exchanged, currency_from);

-- Shedlock table
-- Table used to avoid multiple instances run the same scheduler at same time
CREATE TABLE currency.shedlock (
  name       VARCHAR(64) PRIMARY KEY,
  lock_until TIMESTAMP(3) NULL,
  locked_at  TIMESTAMP(3) NULL,
  locked_by  VARCHAR(255)
);
