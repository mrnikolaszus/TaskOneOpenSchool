--liquibase formatted sql

--changeset nickz:1

INSERT INTO category (id, name, time_created, time_updated)
VALUES (1, 'electric', NOW(), NOW()),
       (2, 'manual', NOW(), NOW()),
       (3, 'tech', NOW(), NOW());

-- Вставка 20 продуктов
INSERT INTO product (name, description, price, category_id, time_created, time_updated)
VALUES ('Product 1', 'Description of Product 1', 100.00, 1, NOW(), NOW()),
       ('Product 2', 'Description of Product 2', 150.00, 2, NOW(), NOW()),
       ('Product 3', 'Description of Product 3', 200.00, 3, NOW(), NOW()),
       ('Product 4', 'Description of Product 4', 250.00, 1, NOW(), NOW()),
       ('Product 5', 'Description of Product 5', 300.00, 2, NOW(), NOW()),
       ('Product 6', 'Description of Product 6', 350.00, 3, NOW(), NOW()),
       ('Product 7', 'Description of Product 7', 400.00, 1, NOW(), NOW()),
       ('Product 8', 'Description of Product 8', 450.00, 2, NOW(), NOW()),
       ('Product 9', 'Description of Product 9', 500.00, 3, NOW(), NOW()),
       ('Product 10', 'Description of Product 10', 550.00, 1, NOW(), NOW()),
       ('Product 11', 'Description of Product 11', 600.00, 2, NOW(), NOW()),
       ('Product 12', 'Description of Product 12', 650.00, 3, NOW(), NOW()),
       ('Product 13', 'Description of Product 13', 700.00, 1, NOW(), NOW()),
       ('Product 14', 'Description of Product 14', 750.00, 2, NOW(), NOW()),
       ('Product 15', 'Description of Product 15', 800.00, 3, NOW(), NOW()),
       ('Product 16', 'Description of Product 16', 850.00, 1, NOW(), NOW()),
       ('Product 17', 'Description of Product 17', 900.00, 2, NOW(), NOW()),
       ('Product 18', 'Description of Product 18', 950.00, 3, NOW(), NOW()),
       ('Product 19', 'Description of Product 19', 1000.00, 1, NOW(), NOW()),
       ('Product 20', 'Description of Product 20', 1050.00, 2, NOW(), NOW());

-- Вставка отзывов для продуктов
INSERT INTO review (text, rate, product_id, time_created, time_updated)
VALUES ('Good product', 5, 1, NOW(), NOW()),
       ('Could be better', 3, 1, NOW(), NOW()),
       ('Excellent', 5, 2, NOW(), NOW()),
       ('Not bad', 4, 3, NOW(), NOW()),
       ('Not what I expected', 2, 5, NOW(), NOW()),
       ('Perfect for my needs', 5, 7, NOW(), NOW()),
       ('Disappointed', 1, 8, NOW(), NOW()),
       ('Very good', 4, 10, NOW(), NOW()),
       ('Great value', 5, 12, NOW(), NOW()),
       ('Satisfied', 4, 13, NOW(), NOW()),
       ('Love it', 5, 15, NOW(), NOW()),
       ('Not great', 2, 17, NOW(), NOW()),
       ('Awesome', 5, 19, NOW(), NOW());