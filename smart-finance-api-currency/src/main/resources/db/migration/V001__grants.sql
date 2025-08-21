-- Grant privileges on a DB to user
grant all privileges on database smart_finance to sf_currency_user;
-- Grant privileges on a Schemas/tables to user
GRANT ALL ON SCHEMA currency TO sf_currency_user;
-- Share Data Schema
-- Grant SELECT privileges on shared_data schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_currency_user;
-- Grant REFERENCE privileges on shared_data schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_currency_user;
-- Grant USAGE privileges on shared_data schema in all tables to users
GRANT USAGE ON SCHEMA shared_data TO sf_currency_user;
