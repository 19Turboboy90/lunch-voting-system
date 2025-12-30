INSERT INTO users (id, name, email, password)
VALUES (100000, 'User', 'user@yandex.ru', '{noop}password'),
       (100001, 'Admin', 'admin@gmail.com', '{noop}admin'),
       (100002, 'Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO restaurant(id, name)
VALUES (100000, 'restaurant_1'),
       (100001, 'restaurant_2');

INSERT INTO menu (id, name, restaurant_id)
VALUES (100000, 'lunch', 100000),
       (100001, 'lunch', 100001);

INSERT INTO dish(id, name, price, description, menu_id)
VALUES (100000, 'egg', 12.12, 'egg', 100000),
       (100001, 'milk', 4.5, 'milk', 100000),
       (100002, 'cookie', 15.2, 'cookie', 100001),
       (100003, 'soda', 10, 'soda', 100001);

INSERT INTO vote(id, user_id, restaurant_id, date_of_vote)
VALUES (100000, 100000, 100000, '2025-12-31'),
       (100001, 100001, 100000, '2025-12-31');