--liquibase formatted sql

--changeset nickz:1

-- Создание таблицы Category
CREATE TABLE IF NOT EXISTS category (
                          id BIGSERIAL PRIMARY KEY ,
                          time_created TIMESTAMP,
                          time_updated TIMESTAMP,
                          name VARCHAR(64) NOT NULL UNIQUE
);

-- Создание таблицы Product
CREATE TABLE IF NOT EXISTS product (
                         id BIGSERIAL PRIMARY KEY,
                         time_created TIMESTAMP,
                         time_updated TIMESTAMP,
                         name VARCHAR(64) NOT NULL UNIQUE,
                         description TEXT,
                         price double precision,
                         category_id INTEGER,
                         FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Создание таблицы Review
CREATE TABLE IF NOT EXISTS review (
                        id BIGSERIAL PRIMARY KEY,
                        time_created TIMESTAMP,
                        time_updated TIMESTAMP,
                        text VARCHAR(255),
                        rate INTEGER,
                        product_id BIGINT,
                        FOREIGN KEY (product_id) REFERENCES product(id)
);

