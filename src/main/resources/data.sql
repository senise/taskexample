-- Insertar usuarios sin especificar el id
INSERT INTO users (name, email, password) VALUES ('Alice', 'alice@example.com','123');
INSERT INTO users (name, email, password) VALUES ('Bob', 'bob@example.com','123');
-- Continuar con el id  en orden
ALTER TABLE users ALTER COLUMN id RESTART WITH 3;

-- Insertar tareas asociadas a los usuarios sin especificar el id
INSERT INTO task (title, description, completed, user_id) VALUES ('Tarea 1', 'Descripción de la tarea 1', false, 1);
INSERT INTO task (title, description, completed, user_id) VALUES ('Tarea 2', 'Descripción de la tarea 2', true, 1);
INSERT INTO task (title, description, completed, user_id) VALUES ('Tarea 3', 'Descripción de la tarea 3', false, 2);
-- Continuar con el id  en orden
ALTER TABLE task ALTER COLUMN id RESTART WITH 4;
