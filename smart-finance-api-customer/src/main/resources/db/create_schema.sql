GRANT ALL PRIVILEGES on database smart_finance TO sf_customer_user;

CREATE SCHEMA IF NOT EXISTS customerDTO;
GRANT ALL ON ALL TABLES IN SCHEMA "customerDTO" TO sf_customer_user;
GRANT ALL PRIVILEGES ON SCHEMA "customerDTO" TO sf_customer_user;
GRANT SELECT ON ALL TABLES IN SCHEMA "shared_data" TO sf_customer_user;

-- to activate uuid_generate_v4()
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";