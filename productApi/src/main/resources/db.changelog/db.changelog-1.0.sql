--liquibase formatted sql

--changeset nickz:1

-- Создание таблицы Category
CREATE TABLE IF NOT EXISTS category (
                          id BIGSERIAL PRIMARY KEY ,
                          name VARCHAR(64) NOT NULL UNIQUE,
                          time_created TIMESTAMP,
                          time_updated TIMESTAMP

);

-- Создание таблицы Product
CREATE TABLE IF NOT EXISTS product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(64) NOT NULL UNIQUE,
                         description VARCHAR(255),
                         price double precision,
                         category_id INTEGER,
                         time_created TIMESTAMP,
                         time_updated TIMESTAMP,
                         FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Создание таблицы Review
CREATE TABLE IF NOT EXISTS review (
                        id BIGSERIAL PRIMARY KEY,
                        text VARCHAR(255),
                        rate INTEGER CHECK (rate >= 0 AND rate <= 5),
                        product_id BIGINT,
                        time_created TIMESTAMP,
                        time_updated TIMESTAMP,
                        FOREIGN KEY (product_id) REFERENCES product(id)
);



