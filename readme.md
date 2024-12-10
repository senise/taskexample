# Task Management API

## Descripción del Proyecto

Este proyecto proporciona una API para gestionar tareas, con funcionalidades de autenticación de usuarios mediante JWT, roles (Admin, User), y acceso a tareas a través de varios filtros y criterios. Se ha implementado un sistema de autenticación y autorización utilizando JWT, y la base de datos se encuentra configurada para su uso con H2 durante el desarrollo.

### Funcionalidades principales:
- Registro y gestión de usuarios (Admin).
- Creación, actualización, obtención y eliminación de tareas.
- Búsqueda de tareas por usuario, título, descripción, o periodo de tiempo.
- Documentación completa de la API a través de Swagger y archivos YAML.

---

## Tecnologías Utilizadas

- **Spring Boot**: Framework principal para el desarrollo de la API.
- **JWT**: Autenticación mediante JSON Web Token para asegurar las rutas de la API.
- **H2 Database**: Base de datos en memoria para el desarrollo.
- **Swagger**: Documentación interactiva de la API.
- **JUnit y Mockito**: Para pruebas unitarias.

---

## Endpoints

A continuación se muestra una lista de los principales endpoints disponibles:

### Usuarios

- `POST /api/v1/users` - **Crear un usuario**
- `GET /api/v1/users` - **Obtener todos los usuarios** (Solo Admin)
- `GET /api/v1/users/{id}` - **Obtener un usuario por ID**
- `PUT /api/v1/users/{id}` - **Actualizar un usuario por ID**
- `DELETE /api/v1/users/{id}` - **Eliminar un usuario por ID**
- `GET /api/v1/users/search` - **Buscar usuarios** (Por nombre, email, rol) (Solo Admin)
- `GET /api/v1/users/created-in-period` - **Buscar usuarios creados en un periodo de tiempo** (Solo Admin)

### Tareas

- `POST /api/v1/tasks` - **Crear una tarea**
- `GET /api/v1/tasks` - **Obtener todas las tareas**
- `GET /api/v1/tasks/{id}` - **Obtener una tarea por ID**
- `PUT /api/v1/tasks/{id}` - **Actualizar una tarea por ID**
- `DELETE /api/v1/tasks/{id}` - **Eliminar una tarea por ID**
- `GET /api/v1/tasks/user/{userId}` - **Obtener tareas por usuario**
- `GET /api/v1/tasks/search` - **Buscar tareas** (Por título, descripción, completada)
- `GET /api/v1/tasks/created-in-period` - **Buscar tareas creadas en un periodo de tiempo**

---

## Autenticación y Autorización

Este proyecto utiliza JWT (JSON Web Token) para la autenticación y autorización de usuarios.

- Para **crear un token JWT**, se realiza una petición `POST` al endpoint `/api/v1/auth/login` con las credenciales del usuario.
- El token se debe incluir en el header de las solicitudes en el campo `Authorization` como `Bearer <TOKEN>`.
- Los **roles** definidos son: `ROLE_USER` y `ROLE_ADMIN`.
    - Los usuarios con el rol `ROLE_ADMIN` tienen acceso completo a la API, incluyendo la gestión de usuarios.
    - Los usuarios con el rol `ROLE_USER` solo pueden gestionar sus propias tareas.

---

## Base de Datos

Se utiliza **H2** como base de datos en memoria en el entorno de desarrollo.

### Tablas Principales

#### **Users**
| Columna       | Tipo           | Descripción                      |
|---------------|----------------|----------------------------------|
| `id`          | `Long`         | ID único del usuario.            |
| `name`        | `String`       | Nombre completo del usuario.     |
| `email`       | `String`       | Correo electrónico del usuario.  |
| `password`    | `String`       | Contraseña del usuario.          |
| `role`        | `String`       | Rol del usuario (Admin/User).    |
| `createdAt`   | `LocalDateTime`| Fecha de creación del usuario.   |

#### **Tasks**
| Columna       | Tipo           | Descripción                      |
|---------------|----------------|----------------------------------|
| `id`          | `Long`         | ID único de la tarea.            |
| `title`       | `String`       | Título de la tarea.              |
| `description` | `String`       | Descripción de la tarea.         |
| `completed`   | `Boolean`      | Estado de la tarea (completada o no). |
| `userId`      | `Long`         | ID del usuario asignado.         |
| `createdAt`   | `LocalDateTime`| Fecha de creación de la tarea.   |

#### **Roles**
| Columna       | Tipo           | Descripción                      |
|---------------|----------------|----------------------------------|
| `id`          | `Long`         | ID único del rol.                |
| `name`        | `String`       | Nombre del rol (por ejemplo, `ROLE_USER`, `ROLE_ADMIN`). |
---

## Swagger

La documentación interactiva de la API está disponible en [Swagger UI](http://localhost:8080/swagger-ui.html). Aquí puedes explorar todos los endpoints, ver ejemplos de respuestas y probar la API directamente.

---

## Directorio `docs`

En la raíz del proyecto, encontrarás un directorio llamado `docs` que contiene dos archivos importantes:

1. **api-docs.yaml**: Definición de la API en formato OpenAPI 3.0.
2. **Postman Collection**: Colección de Postman para facilitar las pruebas de la API.

---

## Cómo Probar la API

1. **Ejecuta la aplicación**:
    - Puedes ejecutar la aplicación a través de `mvn spring-boot:run` o importarla en tu IDE como un proyecto Spring Boot.

2. **Accede a Swagger UI**:
    - Visita [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) para explorar los endpoints de la API.

3. **Realiza peticiones usando Postman**:
    - Importa la colección de Postman desde el directorio `docs` para probar los endpoints rápidamente.

4. **Usa JWT para autenticación**:
    - En cada solicitud que requiera autenticación, incluye el token JWT en los headers de la siguiente manera:
      ```
      Authorization: Bearer <TOKEN>
      ```

---

## H2 Database (Desarrollo)

La base de datos H2 es configurada automáticamente para usarse en modo en memoria durante el desarrollo. Si necesitas consultar los datos en la base de datos, puedes acceder a la consola de H2:

- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:taskexample`
- Usuario: `sa`
- Contraseña: `password`

---
