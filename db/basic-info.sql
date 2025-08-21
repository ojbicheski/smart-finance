-- DBA
--  user: dba
--  pass: dba_admin_password

-- List all users
SELECT usename AS role_name,
  CASE
     WHEN usesuper AND usecreatedb THEN
	   CAST('superuser, create database' AS pg_catalog.text)
     WHEN usesuper THEN
	    CAST('superuser' AS pg_catalog.text)
     WHEN usecreatedb THEN
	    CAST('create database' AS pg_catalog.text)
     ELSE
	    CAST('' AS pg_catalog.text)
  END role_attributes
FROM pg_catalog.pg_user
ORDER BY role_name desc

-- List all roles name
SELECT rolname FROM pg_roles;

-- Create Schema
CREATE SCHEMA IF NOT EXISTS administration;
CREATE SCHEMA IF NOT EXISTS currency;
CREATE SCHEMA IF NOT EXISTS customer;
CREATE SCHEMA IF NOT EXISTS financial_loader;
CREATE SCHEMA IF NOT EXISTS financial_operator;
CREATE SCHEMA IF NOT EXISTS future_launch;
CREATE SCHEMA IF NOT EXISTS investment;
CREATE SCHEMA IF NOT EXISTS report;
CREATE SCHEMA IF NOT EXISTS stock_market;
CREATE SCHEMA IF NOT EXISTS finance;
CREATE SCHEMA IF NOT EXISTS transact;
CREATE SCHEMA IF NOT EXISTS user_management;
CREATE SCHEMA IF NOT EXISTS shared_data;

-- Create roles
CREATE ROLE sf_administration_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_currency_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_customer_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_financial_loader_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_financial_operator_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_future_launch_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_investment_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_report_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_stock_market_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_finance_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_transaction_user LOGIN PASSWORD 'P@ssw0rd';
CREATE ROLE sf_user_management_user LOGIN PASSWORD 'P@ssw0rd';

-- Grant privileges on a DB to user
grant all privileges on database smart_finance to sf_administration_user;
grant all privileges on database smart_finance to sf_currency_user;
grant all privileges on database smart_finance to sf_customer_user;
grant all privileges on database smart_finance to sf_financial_loader_user;
grant all privileges on database smart_finance to sf_financial_operator_user;
grant all privileges on database smart_finance to sf_future_launch_user;
grant all privileges on database smart_finance to sf_investment_user;
grant all privileges on database smart_finance to sf_report_user;
grant all privileges on database smart_finance to sf_stock_market_user;
grant all privileges on database smart_finance to sf_finance_user;
grant all privileges on database smart_finance to sf_transaction_user;
grant all privileges on database smart_finance to sf_user_management_user;

-- Grant privileges on a Schemas/tables to user
GRANT ALL ON SCHEMA administration TO sf_administration_user;
GRANT ALL ON SCHEMA currency TO sf_currency_user;
GRANT ALL ON SCHEMA customer TO sf_customer_user;
GRANT ALL ON SCHEMA financial_loader TO sf_financial_loader_user;
GRANT ALL ON SCHEMA financial_operator TO sf_financial_operator_user;
GRANT ALL ON SCHEMA future_launch TO sf_future_launch_user;
GRANT ALL ON SCHEMA investment TO sf_investment_user;
GRANT ALL ON SCHEMA report TO sf_report_user;
GRANT ALL ON SCHEMA stock_market TO sf_stock_market_user;
GRANT ALL ON SCHEMA finance TO sf_finance_user;
GRANT ALL ON SCHEMA transact TO sf_transaction_user;
GRANT ALL ON SCHEMA user_management TO sf_user_management_user;

-- Customer Schema
-- Grant SELECT privileges on customer schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_administration_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_currency_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_financial_loader_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_financial_operator_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_future_launch_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_investment_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_report_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_stock_market_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_finance_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_transaction_user;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO sf_user_management_user;
-- Grant REFERENCE privileges on customer schema in all tables
GRANT REFERENCES ON ALL TABLES IN SCHEMA customer TO sf_financial_loader_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA customer TO sf_finance_user;
-- Grant USAGE privileges on customer schema in all tables
GRANT USAGE ON SCHEMA customer TO sf_financial_loader_user;
GRANT USAGE ON SCHEMA customer TO sf_finance_user;

-- Share Data Schema
-- Grant SELECT privileges on shared_data schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_administration_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_currency_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_customer_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_financial_loader_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_financial_operator_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_future_launch_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_investment_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_report_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_stock_market_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_finance_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_transaction_user;
GRANT SELECT ON ALL TABLES IN SCHEMA shared_data TO sf_user_management_user;
-- Grant REFERENCE privileges on shared_data schema in all tables to users
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_administration_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_currency_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_customer_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_financial_loader_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_financial_operator_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_future_launch_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_investment_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_report_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_stock_market_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_finance_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_transaction_user;
GRANT REFERENCES ON ALL TABLES IN SCHEMA shared_data TO sf_user_management_user;
-- Grant USAGE privileges on shared_data schema in all tables to users
GRANT USAGE ON SCHEMA shared_data TO sf_administration_user;
GRANT USAGE ON SCHEMA shared_data TO sf_currency_user;
GRANT USAGE ON SCHEMA shared_data TO sf_customer_user;
GRANT USAGE ON SCHEMA shared_data TO sf_financial_loader_user;
GRANT USAGE ON SCHEMA shared_data TO sf_financial_operator_user;
GRANT USAGE ON SCHEMA shared_data TO sf_future_launch_user;
GRANT USAGE ON SCHEMA shared_data TO sf_investment_user;
GRANT USAGE ON SCHEMA shared_data TO sf_report_user;
GRANT USAGE ON SCHEMA shared_data TO sf_stock_market_user;
GRANT USAGE ON SCHEMA shared_data TO sf_finance_user;
GRANT USAGE ON SCHEMA shared_data TO sf_transaction_user;
GRANT USAGE ON SCHEMA shared_data TO sf_user_management_user;

-- Transact Schema
-- Grant SELECT privileges on transact schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA transact TO sf_finance_user;
-- Grant REFERENCE privileges on transact schema in all tables
GRANT REFERENCES ON ALL TABLES IN SCHEMA transact TO sf_finance_user;
-- Grant USAGE privileges on transact schema in all tables
GRANT USAGE ON SCHEMA transact TO sf_finance_user;

-- Currency Schema
-- Grant SELECT privileges on currency schema in all tables to users
GRANT SELECT ON ALL TABLES IN SCHEMA currency TO sf_finance_user;
-- Grant REFERENCE privileges on transact schema in all tables
GRANT REFERENCES ON ALL TABLES IN SCHEMA currency TO sf_finance_user;
-- Grant USAGE privileges on transact schema in all tables
GRANT USAGE ON SCHEMA currency TO sf_finance_user;

-- to activate uuid_generate_v4()
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
