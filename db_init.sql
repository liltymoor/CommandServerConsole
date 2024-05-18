/*
    Для выполнения скрипта зайди под postgres'ом в psql
 */

CREATE USER cmd_server WITH PASSWORD '8877Temur';
CREATE DATABASE cmd_server OWNER cmd_server;

\connect cmd_server cmd_server

CREATE EXTENSION pgcrypto;

CREATE TYPE weapontype AS ENUM(
    'AXE',
    'SHOTGUN',
    'RIFLE',
    'MACHINE_GUN'
    );

CREATE TYPE mood AS ENUM(
    'SADNESS',
    'LONGING',
    'GLOOM',
    'APATHY',
    'RAGE'
    );

CREATE TABLE humanbeing (
                            id SERIAL PRIMARY KEY,
                            name varchar(32) NOT NULL,
                            coord_x real,
                            coord_y bigint CHECK ( coord_y > -195 ) NOT NULL,
                            zoneddt varchar(64) NOT NULL,
                            realhero boolean NOT NULL,
                            hastoothpick boolean NOT NULL,
                            impactspeed bigint NOT NULL,
                            human_weapon weapontype NOT NULL,
                            human_mood mood NOT NULL,
                            carname varchar(16) NOT NULL,
                            entityowner varchar(32) NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username varchar(32) UNIQUE,
                       passwordHash varchar(100)
)

