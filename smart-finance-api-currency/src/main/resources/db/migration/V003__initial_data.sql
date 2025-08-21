INSERT INTO currency.tb_currency
    (version, created, updated, name, code, symbol, active, country_id)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'Canadian dollar', 'CAD', '$', 'true',
    (select id from shared_data.tb_country where code = 'CAD'));

INSERT INTO currency.tb_currency
    (version, created, updated, name, code, symbol, active, country_id)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'Brazilian real', 'BRL', 'R$', 'true',
    (select id from shared_data.tb_country where code = 'BRA'));

INSERT INTO currency.tb_currency
    (version, created, updated, name, code, symbol, active, country_id)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'United States dollar', 'USD', '$', 'true',
    (select id from shared_data.tb_country where code = 'USA'));
