CREATE TABLE IF NOT EXISTS cities (
  id SERIAL PRIMARY KEY,
  name VARCHAR(46),
);

CREATE TABLE IF NOT EXISTS airports (
  id SERIAL PRIMARY KEY,
  name VARCHAR(46),
  city_id INTEGER REFERENCES cities,
  parallel NUMERIC,
  meridian NUMERIC
);

CREATE TABLE IF NOT EXISTS flights (
  id SERIAL PRIMARY KEY,
  airport_from_id INTEGER REFERENCES airports,
  airport_to_id INTEGER REFERENCES airports,
  depature_time TIMESTAMP,
  arrival_time TIMESTAMP,
  cost NUMERIC CHECK (cost > 0),
  airline VARCHAR(46),
  always_late BOOLEAN,
  free_place SMALLINT
);

CREATE TABLE IF NOT EXISTS transfers (
  id SERIAL PRIMARY KEY,
  flight_id INTEGER REFERENCES flights,
  airport_where_id INTEGER REFERENCES airports
);

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  login VARCHAR(46),
  user_role VARCHAR(10),
  password text
);