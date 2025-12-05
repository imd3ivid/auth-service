# Auth Service – Spring Boot + JWT

API REST de autenticación desarrollada con **Spring Boot**, **Spring Security** y **JWT**.  
Permite registrar usuarios, iniciar sesión y acceder a endpoints protegidos usando tokens JWT.

> Proyecto pensado para demostrar conocimientos de backend y seguridad en Java/Spring.

---

## ✨ Funcionalidades

- Registro de usuarios (`/api/auth/register`)
- Login con username + password (`/api/auth/login`)
- Generación de token JWT
- Validación automática del token en cada petición
- Endpoints protegidos con Spring Security
- Acceso a datos solo para usuarios autenticados (`/api/test/me`)
- Persistencia en MySQL usando Spring Data JPA

---

## 🛠️ Tecnologías utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Security
- Spring Data JPA (Hibernate)
- MySQL
- JWT (jjwt)
- Lombok
- Maven

---

## 🧱 Estructura del proyecto

```text
src/main/java/com.imd3ivid.authservice
 ├── config          # Configuración de Spring Security
 ├── controller      # Controladores REST (AuthController, TestController)
 ├── dto             # Clases DTO (peticiones y respuestas)
 ├── entity          # Entidades JPA (User)
 ├── repository      # Repositorios (UserRepository)
 ├── security        # Lógica de seguridad (JWT + UserDetails)
 ├── service         # Lógica de negocio (UserService, AuthService)
 └── AuthServiceApplication.java
⚙️ Configuración y ejecución
1. Requisitos
Java 17+

Maven

MySQL en local

2. Crear base de datos
En MySQL:

sql
Copiar código
CREATE DATABASE auth_service;
3. Configurar application.properties
Ruta: src/main/resources/application.properties

properties
Copiar código
spring.application.name=auth-service

# --- Base de datos ---
spring.datasource.url=jdbc:mysql://localhost:3306/auth_service?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# --- JPA / Hibernate ---
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# --- Puerto del servidor ---
server.port=8080

# --- Configuración JWT ---
app.jwt.secret=mi_clave_secreta_super_segura_que_luego_cambiaremos
app.jwt.expiration=3600000
# 1 hora en milisegundos (1000 * 60 * 60)
⚠️ Para producción la clave JWT se debería mover a variables de entorno.

4. Ejecutar el proyecto
Desde la raíz del proyecto:

bash
Copiar código
mvn spring-boot:run
La API quedará disponible en:

text
Copiar código
http://localhost:8080
🔐 Flujo de autenticación
Registro
El usuario se registra con username, email y password.
La contraseña se cifra con BCrypt antes de guardarse en MySQL.

Login
El usuario envía username + password.

Se valida la contraseña

Si es correcta, se genera un token JWT con el username como subject.

Uso del token
En cada petición a endpoints protegidos se envía el header:

http
Copiar código
Authorization: Bearer <JWT_AQUI>
Filtro JWT
Un filtro (JwtAuthenticationFilter) se ejecuta en cada petición:

Lee el header Authorization

Extrae el token

Valida firma, expiración y username

Carga el usuario desde BD

Si todo está OK, marca la petición como autenticada en Spring Security

📡 Endpoints principales
1. Registro de usuario
URL

http
Copiar código
POST /api/auth/register
Body (JSON)

json
Copiar código
{
  "username": "david",
  "email": "david@example.com",
  "password": "password123"
}
Respuesta (201 Created)

json
Copiar código
{
  "token": null,
  "message": "Usuario registrado correctamente con id: 1"
}
2. Login
URL

http
Copiar código
POST /api/auth/login
Body (JSON)

json
Copiar código
{
  "username": "david",
  "password": "password123"
}
Respuesta (200 OK)

json
Copiar código
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login exitoso"
}
Guarda este token, lo necesitarás para los endpoints protegidos.

3. Endpoint protegido de prueba
URL

http
Copiar código
GET /api/test/me
Headers

h
Copiar código
Authorization: Bearer <TU_JWT>
Respuesta (200 OK)

text
Copiar código
Hola david, estás autenticado correctamente 😎
Si no envías el token o es inválido, obtendrás 401 Unauthorized.

🧪 Cómo probarlo con Postman
Crear petición POST a /api/auth/register (JSON en el body).

Crear petición POST a /api/auth/login y copiar el token de la respuesta.

Crear petición GET a /api/test/me y añadir en Headers:

http
Copiar código
Authorization: Bearer <TU_JWT>
📌 Mejoras futuras (ideas)
Endpoints para gestionar el usuario autenticado (/api/users/me)

Roles y permisos (ROLE_USER, ROLE_ADMIN)

Refresh tokens

Manejo de errores más detallado y códigos específicos

Tests de integración con Spring Boot Test
