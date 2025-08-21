-- Grant privileges on a DB to user
grant all privileges on database smart_finance to sf_finance_user;
-- Grant privileges on a Schemas/tables to user
GRANT ALL ON SCHEMA finance TO sf_finance_user;

-- Share Data Schema
-- Grant SELECT privileges on shared_data schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_finance_user;
-- Grant REFERENCE privileges on shared_data schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_finance_user;
-- Grant USAGE privileges on shared_data schema in all tables to users
GRANT USAGE ON SCHEMA shared_data TO sf_finance_user;

-- Customer Schema
-- Grant SELECT privileges on customer schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_finance_user;
-- Grant REFERENCE privileges on customer schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA customer TO sf_finance_user;
-- Grant USAGE privileges on customer schema in all tables to users
GRANT USAGE ON SCHEMA customer TO sf_finance_user;

-- Currency Schema
-- Grant SELECT privileges on currency schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA currency TO sf_finance_user;
-- Grant REFERENCE privileges on currency schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA currency TO sf_finance_user;
-- Grant USAGE privileges on currency schema in all tables to users
GRANT USAGE ON SCHEMA currency TO sf_finance_user;

-- Transact Schema
-- Grant SELECT privileges on transact schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA transact TO sf_finance_user;
-- Grant REFERENCE privileges on transact schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA transact TO sf_finance_user;
-- Grant USAGE privileges on transact schema in all tables to users
GRANT USAGE ON SCHEMA transact TO sf_finance_user;