--liquibase formatted sql

--changeset nickz:1

INSERT INTO category (id, name) VALUES (1, 'electric');
INSERT INTO category (id, name) VALUES (2, 'manual');
INSERT INTO category (id, name) VALUES (3, 'tech');

