CREATE TABLE cars (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner_name    VARCHAR(255) NOT NULL,
    owner_address VARCHAR(255) NOT NULL,
    car_year      SMALLINT NOT NULL,
    make          VARCHAR(255) NOT NULL,
    model         VARCHAR(255) NOT NULL,
    vin           VARCHAR(17) NOT NULL UNIQUE
);

CREATE TABLE sedans (
    id BIGINT PRIMARY KEY REFERENCES cars (id),
    car_size VARCHAR(255) NOT NULL,
    transmission_type VARCHAR(255) NOT NULL,
    luxury_trim VARCHAR(255) NOT NULL,
    trunk_size DOUBLE PRECISION NOT NULL
);

CREATE TABLE electric_cars (
    id BIGINT PRIMARY KEY REFERENCES cars (id),
    battery_capacity SMALLINT NOT NULL,
    range_miles SMALLINT NOT NULL,
    fast_charging VARCHAR(255) NOT NULL,
    plugin_hybrid VARCHAR(255) NOT NULL
);

CREATE TABLE sports_cars (
    id BIGINT PRIMARY KEY REFERENCES cars (id),
    horsepower SMALLINT NOT NULL,
    zero_to_sixty_time DOUBLE PRECISION NOT NULL,
    top_speed SMALLINT NOT NULL,
    turbocharged VARCHAR(255) NOT NULL
);

CREATE TABLE suvs (
    id BIGINT PRIMARY KEY REFERENCES cars (id),
    seating_capacity SMALLINT NOT NULL,
    cargo_capacity DOUBLE PRECISION NOT NULL,
    drive_type VARCHAR(255) NOT NULL,
    off_road_package VARCHAR(255) NOT NULL
);

CREATE TABLE trucks (
    id BIGINT PRIMARY KEY REFERENCES cars (id),
    cab_type VARCHAR(255) NOT NULL,
    diesel_engine VARCHAR(255) NOT NULL,
    drive_type VARCHAR(255) NOT NULL,
    towing_capacity SMALLINT NOT NULL
);
