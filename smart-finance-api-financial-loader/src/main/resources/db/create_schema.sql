GRANT ALL PRIVILEGES on database smart_finance TO sf_customer_user;

CREATE SCHEMA IF NOT EXISTS financial_loader;
GRANT ALL ON ALL TABLES IN SCHEMA "financial_loader" TO sf_financial_loader_user;
GRANT ALL PRIVILEGES ON SCHEMA "financial_loader" TO sf_financial_loader_user;
GRANT SELECT ON ALL TABLES IN SCHEMA "shared_data" TO sf_financial_loader_user;

