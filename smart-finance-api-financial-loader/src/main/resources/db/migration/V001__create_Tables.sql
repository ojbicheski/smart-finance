-- File table
CREATE TABLE IF NOT EXISTS financial_loader.tb_file
(
    id          BIGSERIAL    PRIMARY KEY,
    reference   UUID         NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER      NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    file_name   VARCHAR(255) NOT NULL,
    status      VARCHAR(50)  NOT NULL,
    account_id  BIGINT       NOT NULL,
    CONSTRAINT fk_account_id
      FOREIGN KEY(account_id)
        REFERENCES customer.tb_account(id)
);

ALTER TABLE financial_loader.tb_file
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_loader.tb_file
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_file_reference ON financial_loader.tb_file (reference);
CREATE INDEX idx_account_id ON financial_loader.tb_file (account_id);
CREATE INDEX idx_account_id_status ON financial_loader.tb_file (account_id, status);

-- File line table
CREATE TABLE IF NOT EXISTS financial_loader.tb_file_line
(
    id          BIGSERIAL     PRIMARY KEY,
    reference   UUID          NOT NULL DEFAULT public.uuid_generate_v4(),
    version     INTEGER       NOT NULL,
    created     TIMESTAMP     NOT NULL,
    updated     TIMESTAMP     NOT NULL,
    line_number INT           NOT NULL,
    line        VARCHAR(2000) NOT NULL,
    status      VARCHAR(50)   NOT NULL,
    reason_fail VARCHAR(1000)     NULL,
    file_id     BIGINT        NOT NULL,
    CONSTRAINT fk_file_id
      FOREIGN KEY(file_id)
        REFERENCES financial_loader.tb_file(id)
);

ALTER TABLE financial_loader.tb_file_line
    ALTER COLUMN created
    TYPE TIMESTAMP WITH TIME ZONE USING created AT TIME ZONE 'UTC';
ALTER TABLE financial_loader.tb_file_line
    ALTER COLUMN updated
    TYPE TIMESTAMP WITH TIME ZONE USING updated AT TIME ZONE 'UTC';
CREATE UNIQUE INDEX u_tb_file_line_reference ON financial_loader.tb_file_line (reference);
CREATE INDEX idx_tb_file_line_file_id ON financial_loader.tb_file_line (file_id);
