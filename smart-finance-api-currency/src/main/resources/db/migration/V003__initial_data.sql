INSERT INTO currency.tb_currency
    (version, created, updated, name, code, symbol, country_id)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'Canadian dollar', 'CAD', '$',
    (select id from shared_data.tb_country where code = 'CAD'));

INSERT INTO currency.tb_currency
    (version, created, updated, name, code, symbol, country_id)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'Brazilian real', 'BRL', 'R$',
    (select id from shared_data.tb_country where code = 'BRA'));

INSERT INTO currency.tb_currency
    (version, created, updated, name, code, symbol, country_id)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'United States dollar', 'USD', '$',
    (select id from shared_data.tb_country where code = 'USA'));
