INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant(name)
VALUES ('restaurant_1'),
       ('restaurant_2');

INSERT INTO menu (name, restaurant_id)
VALUES ('lunch', 1),
       ('lunch', 2);

INSERT INTO dish(name, price, description, menu_id)
VALUES ('egg', 12.12, 'egg', 1),
       ('milk', 4.5, 'milk', 1),
       ('cookie', 15.2, 'cookie', 2),
       ('soda', 10, 'soda', 2);

INSERT INTO vote(user_id, restaurant_id, date_of_vote)
VALUES (1, 1, CURRENT_DATE),
       (2, 2, CURRENT_DATE);