DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (id, name, email, password)
VALUES (100000, 'User', 'user@yandex.ru', 'password');

INSERT INTO users (id, name, email, password)
VALUES (100001, 'Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, TIMESTAMP '2015-05-30 10:00', 'Завтрак', 1000),
  (100000, TIMESTAMP '2015-05-30 13:00', 'Обед', 500),
  (100000, TIMESTAMP '2015-05-30 20:00', 'Ужин', 510),
  (100000, TIMESTAMP '2015-05-31 10:00', 'Завтрак', 1000),
  (100000, TIMESTAMP '2015-05-31 13:00', 'Обед', 500),
  (100000, TIMESTAMP '2015-05-31 20:00', 'Ужин', 500),
  (100001, TIMESTAMP '2015-05-30 13:00', 'Завтрак', 1000),
  (100001, TIMESTAMP '2015-05-30 13:00', 'Обед', 500),
  (100001, TIMESTAMP '2015-05-30 20:00', 'Ужин', 510),
  (100001, TIMESTAMP '2015-05-31 13:00', 'Завтрак', 1000),
  (100001, TIMESTAMP '2015-05-31 13:00', 'Обед', 500),
  (100001, TIMESTAMP '2015-05-31 20:00', 'Ужин', 500);

