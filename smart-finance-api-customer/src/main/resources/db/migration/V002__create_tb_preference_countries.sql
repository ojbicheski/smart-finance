-- Preference Countries table
CREATE TABLE IF NOT EXISTS customer.tb_preference_countries
(
    id          BIGSERIAL PRIMARY KEY,
    reference   UUID      NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER   NOT NULL,
    created     TIMESTAMP NOT NULL,
    updated     TIMESTAMP NOT NULL,
    customer_id BIGINT    NOT NULL,
    country_id  BIGINT    NOT NULL,
    CONSTRAINT fk_preference_countries_customer_id
      FOREIGN KEY(customer_id)
        REFERENCES customer.tb_customer(id),
    CONSTRAINT fk_preference_countries_country_id
      FOREIGN KEY(country_id)
        REFERENCES shared_data.tb_country(id)
);

ALTER TABLE customer.tb_preference_countries
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE customer.tb_preference_countries
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_preference_countries_reference
    ON customer.tb_preference_countries (reference);
