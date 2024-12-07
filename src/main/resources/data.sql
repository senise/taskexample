-- Insertar roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
-- Continuar con el id en orden
ALTER TABLE roles ALTER COLUMN id RESTART WITH 3;

-- Insertar usuarios asignando un rol directamente
-- Alice tiene ROLE_USER (contraseña encriptada)
INSERT INTO users (name, email, password, role_id, created_at) 
VALUES 
('Alice', 'alice@example.com', '$2a$10$dABKA5WpAW3h5mqcvrAJretqsW1xA0EClrQjwI6l/ndaro5Z.m93y', 1, NOW());  -- Contraseña '123' encriptada

-- Bob tiene ROLE_ADMIN (contraseña encriptada)
INSERT INTO users (name, email, password, role_id, created_at) 
VALUES 
('Bob', 'bob@example.com', '$2a$10$dABKA5WpAW3h5mqcvrAJretqsW1xA0EClrQjwI6l/ndaro5Z.m93y', 2, NOW());  -- Contraseña '123' encriptada

-- Continuar con el id en orden
ALTER TABLE users ALTER COLUMN id RESTART WITH 3;

-- Insertar tareas asociadas a los usuarios
-- Tarea de Alice
INSERT INTO task (title, description, completed, user_id, created_at) 
VALUES 
('Tarea 1', 'Descripción de la tarea 1', false, 1, NOW());

-- Tarea de Alice
INSERT INTO task (title, description, completed, user_id, created_at) 
VALUES 
('Tarea 2', 'Descripción de la tarea 2', true, 1, NOW());

-- Tarea de Bob
INSERT INTO task (title, description, completed, user_id, created_at) 
VALUES 
('Tarea 3', 'Descripción de la tarea 3', false, 2, NOW());

-- Continuar con el id en orden
ALTER TABLE task ALTER COLUMN id RESTART WITH 4;
