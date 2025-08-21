GRANT ALL PRIVILEGES on database smart_finance TO sf_currency_user;

CREATE SCHEMA IF NOT EXISTS currency;

GRANT ALL ON ALL TABLES IN SCHEMA "currency" TO sf_currency_user;
GRANT ALL PRIVILEGES ON SCHEMA "currency" TO sf_currency_user;
GRANT SELECT ON ALL TABLES IN SCHEMA "shared_data" TO sf_currency_user;
