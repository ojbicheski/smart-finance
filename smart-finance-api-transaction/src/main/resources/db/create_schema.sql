CREATE SCHEMA IF NOT EXISTS transact;
GRANT ALL ON ALL TABLES IN SCHEMA "transact" TO sf_transaction_user;
GRANT ALL PRIVILEGES ON SCHEMA "transact" TO sf_transaction_user;