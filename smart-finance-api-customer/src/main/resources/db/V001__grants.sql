-- Grant privileges on a DB to user
GRANT ALL PRIVILEGES on database smart_finance to sf_customer_user;
-- Grant privileges on a Schemas/tables to user
GRANT ALL ON SCHEMA customer TO sf_customer_user;

-- Share Data Schema
-- Grant SELECT privileges on shared_data schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_customer_user;
-- Grant REFERENCE privileges on shared_data schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_customer_user;
-- Grant USAGE privileges on shared_data schema in all tables to users
GRANT USAGE ON SCHEMA shared_data TO sf_customer_user;

-- Financial Operator Schema
-- Grant SELECT privileges on financial_operator schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA financial_operator TO sf_customer_user;
-- Grant REFERENCE privileges on financial_operator schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA financial_operator TO sf_customer_user;
-- Grant USAGE privileges on financial_operator schema in all tables to users
GRANT USAGE ON SCHEMA financial_operator TO sf_customer_user;

-- Currency Schema
-- Grant SELECT privileges on currency schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA currency TO sf_customer_user;
-- Grant REFERENCE privileges on currency schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA currency TO sf_customer_user;
-- Grant USAGE privileges on currency schema in all tables to users
GRANT USAGE ON SCHEMA currency TO sf_customer_user;
