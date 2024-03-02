--liquibase formatted sql

--changeset nickz:1

-- Вставка категорий
INSERT INTO category (id, name, time_created, time_updated)
VALUES
       (1, 'electric', NOW(), NOW()),
       (2, 'manual', NOW(), NOW()),
       (3, 'tech', NOW(), NOW());

SELECT SETVAL('category_id_seq', (SELECT MAX(id) FROM category));

-- Вставка 20 продуктов
INSERT INTO product (id, name, description, price, category_id, time_created, time_updated)
VALUES
       (1, 'Product 1', 'Description of Product 1', 100.00, 1, NOW(), NOW()),
       (2, 'Product 2', 'Description of Product 2', 150.00, 2, NOW(), NOW()),
       (3, 'Product 3', 'Description of Product 3', 200.00, 3, NOW(), NOW()),
       (4, 'Product 4', 'Description of Product 4', 250.00, 1, NOW(), NOW()),
       (5, 'Product 5', 'Description of Product 5', 300.00, 2, NOW(), NOW()),
       (6, 'Product 6', 'Description of Product 6', 350.00, 3, NOW(), NOW()),
       (7, 'Product 7', 'Description of Product 7', 400.00, 1, NOW(), NOW()),
       (8, 'Product 8', 'Description of Product 8', 450.00, 2, NOW(), NOW()),
       (9, 'Product 9', 'Description of Product 9', 500.00, 3, NOW(), NOW()),
       (10, 'Product 10', 'Description of Product 10', 550.00, 1, NOW(), NOW()),
       (11, 'Product 11', 'Description of Product 11', 600.00, 2, NOW(), NOW()),
       (12, 'Product 12', 'Description of Product 12', 650.00, 3, NOW(), NOW()),
       (13, 'Product 13', 'Description of Product 13', 700.00, 1, NOW(), NOW()),
       (14, 'Product 14', 'Description of Product 14', 750.00, 2, NOW(), NOW()),
       (15, 'Product 15', 'Description of Product 15', 800.00, 3, NOW(), NOW()),
       (16, 'Product 16', 'Description of Product 16', 850.00, 1, NOW(), NOW()),
       (17, 'Product 17', 'Description of Product 17', 900.00, 2, NOW(), NOW()),
       (18, 'Product 18', 'Description of Product 18', 950.00, 3, NOW(), NOW()),
       (19, 'Product 19', 'Description of Product 19', 1000.00, 1, NOW(), NOW()),
       (20, 'Product 20', 'Description of Product 20', 1050.00, 2, NOW(), NOW());

SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));

-- Вставка отзывов для продуктов
INSERT INTO review (id, text, rate, product_id, time_created, time_updated)
VALUES
       (1, 'Good product', 5, 1, NOW(), NOW()),
       (2, 'Could be better', 3, 1, NOW(), NOW()),
       (3, 'Excellent', 5, 2, NOW(), NOW()),
       (4, 'Not bad', 4, 3, NOW(), NOW()),
       (5, 'Not what I expected', 2, 5, NOW(), NOW()),
       (6, 'Perfect for my needs', 5, 7, NOW(), NOW()),
       (7, 'Disappointed', 1, 8, NOW(), NOW()),
       (8, 'Very good', 4, 10, NOW(), NOW()),
       (9, 'Great value', 5, 12, NOW(), NOW()),
       (10, 'Satisfied', 4, 13, NOW(), NOW()),
       (11, 'Love it', 5, 15, NOW(), NOW()),
       (12, 'Not great', 2, 17, NOW(), NOW()),
       (13, 'Awesome', 5, 19, NOW(), NOW());

SELECT SETVAL('review_id_seq', (SELECT MAX(id) FROM review));