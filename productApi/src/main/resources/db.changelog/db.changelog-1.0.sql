--liquibase formatted sql

--changeset nickz:1

-- Создание таблицы Category
CREATE TABLE IF NOT EXISTS category (
                          id SERIAL PRIMARY KEY ,
                          name VARCHAR(64) NOT NULL UNIQUE
);

-- Создание таблицы Product
CREATE TABLE IF NOT EXISTS product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(64) NOT NULL UNIQUE,
                         description TEXT,
                         price DECIMAL(10, 2),
                         category_id INTEGER,
                         FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Создание таблицы Review
CREATE TABLE IF NOT EXISTS review (
                        id SERIAL PRIMARY KEY,
                        text VARCHAR(255),
                        rate INTEGER,
                        product_id BIGINT,
                        FOREIGN KEY (product_id) REFERENCES product(id)
);

