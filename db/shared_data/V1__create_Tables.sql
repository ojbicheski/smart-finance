CREATE TABLE IF NOT EXISTS shared_data.tb_country
(
    id          BIGSERIAL       PRIMARY KEY,
    reference   UUID            NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER         NOT NULL,
    created     TIMESTAMP       NOT NULL,
    updated     TIMESTAMP       NOT NULL,
    name        VARCHAR(255)    NOT NULL,
    code        VARCHAR(3)      NOT NULL,
    display     VARCHAR(255)    NOT NULL
);

ALTER TABLE shared_data.tb_country
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE shared_data.tb_country
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';

CREATE UNIQUE INDEX uk_country_reference ON shared_data.tb_country (reference);
CREATE UNIQUE INDEX uk_country_code ON shared_data.tb_country (code);