set schema 'public';
INSERT INTO category(uuid, name, description, register_date)
VALUES ('f6eba314-0ea2-48cc-9a19-c5790c887c85', 'Sandwich', 'Sandwich  Categories', now());
INSERT INTO category(uuid, name, description, register_date)
VALUES ('74b3dbae-3fdb-4a9f-999b-ea9f027b671e', 'Sides', 'Side dish Categories', now());
INSERT INTO category(uuid, name, description, register_date)
VALUES ('a930551c-55b3-4687-9ebd-5525f0baf6da', 'Drink', 'Drink Categories', now());
INSERT INTO category(uuid, name, description, register_date)
VALUES ('52ad266d-bcb5-4101-a8e1-cc45cc83afad', 'Dessert', 'Dessert Categories', now());

--insert lanches
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (1, 'b1f859e6-07df-4b67-a1cd-74d946442207', 'Hamburger', 'Hamburger', 20.50, now());
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (1, 'ea1697ef-b794-45c0-b2bb-ebd36803ece3', 'Hot Dog', 'Hot Dog', 15.25, now());

--insert acompanhamentos
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (2, '68a589ce-979f-4350-bcd6-ca049f3beb16', ' French fries', ' French fries 100g', 10.75, now());
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (2, '3f6b7762-8798-4c45-a3f6-105406e8baaf', 'Onions rings 100g', 'Onions rings 100g', 15.25, now());

--insert bebida
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (3, '56199d36-969b-4e1b-9515-f84ffed6a19b', 'soft drink', 'Coca cola zero 350 ml', 8.50, now());
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (3, 'a763f6ce-1122-434b-9721-b348db2894e6', 'soft drink', 'Coca cola 350 ml', 8.50, now());

--insert sobremesa
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (4, '7b3c010c-9f03-4a56-8c85-b519a5f6b86e', 'Milk Flan', 'Milk Flan 100g', 12.75, now());
INSERT INTO product(id_category, uuid, name, description, price, register_date)
VALUES (4, 'f6d7f266-33d9-42aa-a2ee-63d2e08f5176', 'Cake', 'Chocolate Cake Slice 100g', 13.45, now());

--insert customer
INSERT INTO customer(uuid, name, document, email, register_date)
VALUES ('3fa85f64-5717-4562-b3fc-2c963f66afa6', 'Myller Sakaguchi', '88404071039', 'myllersakaguchi@gmail.com', now());
