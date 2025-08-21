-- Operator table
CREATE TABLE IF NOT EXISTS financial_operator.tb_operator
(
    id         BIGSERIAL    PRIMARY KEY,
    reference  UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version    INTEGER      NOT NULL,
    created    TIMESTAMP    NOT NULL,
    updated    TIMESTAMP    NOT NULL,
    swift_code VARCHAR(15)  NOT NULL,
    name       VARCHAR(255) NOT NULL,
    display    VARCHAR(100) NOT NULL,
    active     BOOLEAN      NOT NULL,
    country_id BIGINT       NOT NULL,
    CONSTRAINT fk_country_id
      FOREIGN KEY(country_id)
        REFERENCES shared_data.tb_country(id)
);

ALTER TABLE financial_operator.tb_operator
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_operator.tb_operator
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX IF NOT EXISTS tb_operator_reference ON financial_operator.tb_operator (reference);
CREATE UNIQUE INDEX IF NOT EXISTS tb_operator_swift_code ON financial_operator.tb_operator (swift_code);

-- Support table for File
-- Format table
CREATE TABLE IF NOT EXISTS financial_operator.tb_format_file
(
    id          BIGSERIAL  PRIMARY KEY,
    reference   UUID       NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER    NOT NULL,
    created     TIMESTAMP  NOT NULL,
    updated     TIMESTAMP  NOT NULL,
    extension   VARCHAR(5) NOT NULL,
    active      BOOLEAN    NOT NULL,
    operator_id BIGINT     NOT NULL,
    CONSTRAINT fk_operator_id
      FOREIGN KEY(operator_id)
        REFERENCES financial_operator.tb_operator(id)
);

ALTER TABLE financial_operator.tb_operator
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_operator.tb_operator
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';

-- Template table
CREATE TABLE IF NOT EXISTS financial_operator.tb_template_file
(
    id               BIGSERIAL    PRIMARY KEY,
    reference        UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version          INTEGER      NOT NULL,
    created          TIMESTAMP    NOT NULL,
    updated          TIMESTAMP    NOT NULL,
    name             VARCHAR(100) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    template_version VARCHAR(20)  NOT NULL,
    layout           JSONB        NOT NULL,
    active           BOOLEAN      NOT NULL,
    format_id        BIGINT       NOT NULL,
    operator_id      BIGINT       NOT NULL,
    CONSTRAINT fk_format_id
      FOREIGN KEY(format_id)
        REFERENCES financial_operator.tb_format_file(id),
    CONSTRAINT fk_operator_id
      FOREIGN KEY(operator_id)
        REFERENCES financial_operator.tb_operator(id)
);

ALTER TABLE financial_operator.tb_template_file
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_operator.tb_template_file
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX IF NOT EXISTS tb_template_file_reference ON financial_operator.tb_template_file (reference);
CREATE UNIQUE INDEX IF NOT EXISTS tb_template_file_name ON financial_operator.tb_template_file (name);

-- Product Type table
CREATE TABLE IF NOT EXISTS financial_operator.tb_product_type
(
    id          BIGSERIAL    PRIMARY KEY,
    reference   UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER      NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    code        VARCHAR(100) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    active      BOOLEAN      NOT NULL,
    operator_id BIGINT       NOT NULL,
    CONSTRAINT fk_operator_id
      FOREIGN KEY(operator_id)
        REFERENCES financial_operator.tb_operator(id)
);

ALTER TABLE financial_operator.tb_product_type
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_operator.tb_product_type
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX IF NOT EXISTS tb_product_type_reference ON financial_operator.tb_product_type (reference);
CREATE UNIQUE INDEX IF NOT EXISTS tb_product_type_code ON financial_operator.tb_product_type (code);

-- Product table
CREATE TABLE IF NOT EXISTS financial_operator.tb_product
(
    id               BIGSERIAL    PRIMARY KEY,
    reference        UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version          INTEGER      NOT NULL,
    created          TIMESTAMP    NOT NULL,
    updated          TIMESTAMP    NOT NULL,
    code             VARCHAR(100) NOT NULL,
    name             VARCHAR(255) NOT NULL,
    active           BOOLEAN      NOT NULL,
    type_id          BIGINT       NOT NULL,
    template_file_id BIGINT       NULL,
    CONSTRAINT fk_type_id
      FOREIGN KEY(type_id)
        REFERENCES financial_operator.tb_product_type(id),
    CONSTRAINT fk_prodct_template_file_id
      FOREIGN KEY(template_file_id)
        REFERENCES financial_operator.tb_template_file(id)
);

ALTER TABLE financial_operator.tb_product
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_operator.tb_product
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX IF NOT EXISTS tb_product_reference ON financial_operator.tb_product (reference);
CREATE UNIQUE INDEX IF NOT EXISTS tb_product_code ON financial_operator.tb_product (code);

-- Product table
CREATE TABLE IF NOT EXISTS financial_operator.tb_product_detail
(
    id               BIGSERIAL       PRIMARY KEY,
    reference        UUID          NOT NULL DEFAULT public.uuid_generate_v4(),
    version          INTEGER       NOT NULL,
    created          TIMESTAMP     NOT NULL,
    updated          TIMESTAMP     NOT NULL,
    name             VARCHAR(100)  NOT NULL,
    description      VARCHAR(1000) NOT NULL,
    active           BOOLEAN       NOT NULL,
    product_id       BIGINT        NOT NULL,
    template_file_id BIGINT        NULL,
    CONSTRAINT fk_product_id
      FOREIGN KEY(product_id)
        REFERENCES financial_operator.tb_product(id),
    CONSTRAINT fk_prodt_dtl_template_file_id
      FOREIGN KEY(template_file_id)
        REFERENCES financial_operator.tb_template_file(id)
);

ALTER TABLE financial_operator.tb_product_detail
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_operator.tb_product_detail
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX IF NOT EXISTS tb_product_detail_reference ON financial_operator.tb_product_detail (reference);
CREATE UNIQUE INDEX IF NOT EXISTS tb_product_detail_name ON financial_operator.tb_product_detail (name);
