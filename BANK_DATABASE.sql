CREATE DATABASE Bank;
USE Bank;

CREATE TABLE accounts(
  account_number bigint(200) NOT NULL PRIMARY KEY,
  full_name VARCHAR(255) NOT NULL,
  email VARCHAR(255)UNIQUE,
  balance DECIMAL(10.0) NOT NULL,
  security_pin char(10) NOT NULL
);


CREATE TABLE user(
  full_name VARCHAR(55),
  email VARCHAR(55) PRIMARY KEY,
  password VARCHAR(55)
);

SELECT * FROM accounts;
