-- Insertar roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
-- Continuar con el id en orden
ALTER TABLE roles ALTER COLUMN id RESTART WITH 3;

-- Insertar usuarios asignando un rol directamente
-- Alice tiene ROLE_USER
INSERT INTO users (name, email, password, role_id) VALUES ('Alice', 'alice@example.com', '123', 1); -- Cambia '123' por una contraseña encriptada
-- Bob tiene ROLE_ADMIN
INSERT INTO users (name, email, password, role_id) VALUES ('Bob', 'bob@example.com', '123', 2); -- Cambia '123' por una contraseña encriptada
-- Continuar con el id en orden
ALTER TABLE users ALTER COLUMN id RESTART WITH 3;

-- Insertar tareas asociadas a los usuarios
INSERT INTO task (title, description, completed, user_id) VALUES ('Tarea 1', 'Descripción de la tarea 1', false, 1); -- Tarea de Alice
INSERT INTO task (title, description, completed, user_id) VALUES ('Tarea 2', 'Descripción de la tarea 2', true, 1); -- Tarea de Alice
INSERT INTO task (title, description, completed, user_id) VALUES ('Tarea 3', 'Descripción de la tarea 3', false, 2); -- Tarea de Bob
-- Continuar con el id en orden
ALTER TABLE task ALTER COLUMN id RESTART WITH 4;
