#!/bin/bash

mariadb -u root
CREATE DATABASE users

CREATE TABLE credential
(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(10) NOT NULL,
    password VARCHAR(50) NOT NULL,
    salt VARCHAR(50) NOT NULL
);

CREATE TABLE profile
(
    name VARCHAR(10) NOT NULL,
    avatar VARCHAR(50),
    id references credential (id)
); 