DROP DATABASE springproj1;
DROP USER springproj1;

CREATE USER springproj1 with password 'password1';
CREATE DATABASE springproj1 with template=template0 owner=springproj1;
\connect springproj1;
ALTER DEFAULT PRIVILEGES GRANT ALL ON tables TO springproj1;
ALTER DEFAULT PRIVILEGES GRANT ALL ON sequences TO springproj1;

CREATE TABLE users(
user_id INTEGER PRIMARY KEY NOT NULL,
name VARCHAR(60) NOT NULL,
phone_number VARCHAR(13) NOT NULL UNIQUE,
hashed_password TEXT NOT NULL
);

CREATE SEQUENCE users_seq INCREMENT 1 START 1;
