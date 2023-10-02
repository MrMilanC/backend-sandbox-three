INSERT INTO user (first_name, last_name, password, role, username, email) VALUES ('Max', 'Mustermann', '$2a$12$FEwVGMzao/HRCbgKk1pAdurBLs.4VvxU0JkDM54x.Giw5pZTlh/Nu', 'ROLE_USER', 'user', 'max@email.at');
INSERT INTO user (first_name, last_name, password, role, username, email) VALUES ('Dolores', 'Bauer', '$2a$12$FEwVGMzao/HRCbgKk1pAdurBLs.4VvxU0JkDM54x.Giw5pZTlh/Nu', 'ROLE_ADMIN', 'admin', 'dolores@email.at');

INSERT INTO category (category_name) VALUES ('Saefte');
INSERT INTO category (category_name) VALUES ('Gemuese');
INSERT INTO category (category_name) VALUES ('Obst');
INSERT INTO category (category_name) VALUES ('Tierprodukte');

INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('Orangensaft pur', 'juiceOrange.jpg', 2.35, 'Orangensaft', 1, 7);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('frische Paprika', 'paprika.jpg', 4.87, 'Paprika', 2, 10);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('frische Äpfel', 'apple.jpg', 3.34, 'Apfel', 3, 3);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('frische Milch', 'milk.jpg', 5.65, 'Milch', 4, 6);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('frische Tomaten', 'tomaten.jpg', 7.56, 'Tomate', 2, 4);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('frische Karotten', 'karotte.jpg', 2.31, 'Karotte', 2, 7);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('frischer Käse', 'cheese.jpg', 7.99, 'Käse', 4, 7);
INSERT INTO product (description, image_name, price, product_name, category_id, quantity) VALUES ('Apfelsaft pur', 'juiceApple.jpg', 6.75, 'Apfelsaft', 1, 7);
