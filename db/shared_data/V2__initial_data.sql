INSERT INTO shared_data.tb_country
    (version, created, updated, name, code, display)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'Dominion of Canada', 'CAD', 'Canada');

INSERT INTO shared_data.tb_country
    (version, created, updated, name, code, display)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'United States of America', 'USA', 'United States');

INSERT INTO shared_data.tb_country
    (version, created, updated, name, code, display)
VALUES
    (0, now() AT TIME ZONE 'UTC', now() AT TIME ZONE 'UTC', 'Federative Republic of Brazil', 'BRA', 'Brazil');
