-- Alter table Transact
--- Adding column to manage balance update
ALTER TABLE transact.tb_transact
    ADD balanced BOOLEAN NOT NULL DEFAULT FALSE;

-- Shedlock table
-- Table used to avoid multiple instances run the same scheduler at same time
CREATE TABLE IF NOT EXISTS transact.shedlock (
  name       VARCHAR(64) PRIMARY KEY,
  lock_until TIMESTAMP(3) NULL,
  locked_at  TIMESTAMP(3) NULL,
  locked_by  VARCHAR(255)
);
