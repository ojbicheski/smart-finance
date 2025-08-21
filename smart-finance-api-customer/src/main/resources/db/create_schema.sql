GRANT ALL PRIVILEGES on database smart_finance TO sf_customer_user;

CREATE SCHEMA IF NOT EXISTS customer;
GRANT ALL ON ALL TABLES IN SCHEMA "customer" TO sf_customer_user;
GRANT ALL PRIVILEGES ON SCHEMA "customer" TO sf_customer_user;

-- to activate uuid_generate_v4()
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";