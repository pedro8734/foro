# 🧠 Foro API

API RESTful para un foro de discusión construido con **Java + Spring Boot**. Ofrece autenticación mediante JWT, gestión de usuarios, temas (tópicos) y respuestas. Incluye control de roles y estados para una gestión avanzada del contenido y usuarios.

## 🚀 Tecnologías y Herramientas Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security (JWT Stateless)**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **Flyway** – para control de versiones de base de datos
- **MySQL** – como base de datos relacional
- **Swagger (Springdoc OpenAPI)** – documentación automática de la API
- **Lombok** – reducción de código repetitivo
- **ModelMapper** – para convertir DTOs a entidades
- **Maven** – gestor de dependencias
- **Postman / Insomnia** – para probar los endpoints

## 📌 Funcionalidades Principales

- Autenticación vía JWT
- Registro y login de usuarios
- Roles: `CLIENTE` y `ADMINISTRADOR`
- Estados de usuario: `ACTIVO`, `ELIMINADO` (borrado lógico)
- Crear, ver, editar y eliminar tópicos (temas)
- Crear, ver, editar y eliminar respuestas
- Estados del tópico: `NO_RESPONDIDO`, `RESPONDIDO`, `SOLUCIONADO`
- Solo el autor puede editar/borrar sus respuestas
- Solo el ADMINISTRADOR puede marcar una respuesta como solución
- Paginación y ordenamiento
---
## 📡 Endpoints de la API

| Método | Endpoint                  | Descripción                                             | Seguridad         |
|--------|---------------------------|---------------------------------------------------------|-------------------|
| POST   | `/login`                  | Autenticación (devuelve token JWT)                      | Público           |
| POST   | `/usuarios`               | Registro de nuevo usuario (rol CLIENTE por defecto)     | Público           |
| GET    | `/usuarios`               | Lista usuarios activos                                  | Requiere token    |
| DELETE | `/usuarios/{id}`          | Elimina lógicamente un usuario (solo ADMIN)             | Solo ADMIN        |
| POST   | `/topicos`                | Crear nuevo tópico                                      | Requiere token    |
| GET    | `/topicos`                | Ver todos los tópicos activos                           | Requiere token    |
| GET    | `/topicos/{id}`           | Ver detalle de un tópico                                | Requiere token    |
| PUT    | `/topicos/{id}`           | Editar tópico (solo autor)                              | Requiere token    |
| DELETE | `/topicos/{id}`           | Eliminar tópico (solo autor, lógico)                    | Requiere token    |
| POST   | `/respuestas`             | Crear respuesta (cambia estado del tópico)              | Requiere token    |
| GET    | `/respuestas`             | Listar respuestas activas (paginado)                    | Requiere token    |
| GET    | `/respuestas/{id}`        | Detalle de una respuesta                                | Requiere token    |
| PUT    | `/respuestas/{id}`        | Editar respuesta (solo autor)                           | Requiere token    |
| DELETE | `/respuestas/{id}`        | Eliminar respuesta (lógico, solo autor)                 | Requiere token    |
| PATCH  | `/respuestas/{id}/marcar-solucion` | Marcar respuesta como solución (solo ADMIN)           | Solo ADMIN        |
| GET    | `/swagger-ui/index.html`  | Swagger UI                                              | Público           |
| GET    | `/v3/api-docs`            | Documentación OpenAPI JSON                              | Público           |

---

## 🔐 Seguridad y Roles

| Elemento             | Detalles                                                      |
|----------------------|---------------------------------------------------------------|
| JWT                  | Autenticación stateless mediante tokens JWT                   |
| Roles                | `CLIENTE` y `ADMINISTRADOR`                                   |
| Borrado lógico       | Usuarios y respuestas no se eliminan de la DB, solo se inactivan |
| Validación de autor  | Solo el autor puede editar o eliminar su contenido            |
| Marcar solución      | Solo el `ADMINISTRADOR` puede marcar una respuesta como solución |

---

## 🧱 Entidades del Sistema

| Entidad     | Campos Clave                                                       |
|-------------|---------------------------------------------------------------------|
| `Usuario`   | id, nombre, correo, contraseña, rol (enum), estado (`ACTIVO/ELIMINADO`) |
| `Topico`    | id, titulo, mensaje, autor, estado (`NO_RESPONDIDO`, etc.), respuestas |
| `Respuesta` | id, mensaje, autor, topico, fechaCreacion, solucion, estado (`ACTIVO/ELIMINADO`) |


### 🔹 Capas de Servicio
| **Servicio**            | **Función**                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `AutenticacionService`   | Valida credenciales y gestiona el proceso de login                          |
| `JWTService`            | Genera, valida y decodifica tokens JWT                                      |

### 🔹 Infraestructura
| **Componente**           | **Responsabilidad**                                                         |
|--------------------------|-----------------------------------------------------------------------------|
| `SecurityFilter`         | Filtro que intercepta solicitudes y valida tokens                           |
| `UsuarioRepository`      | Capa de acceso a datos para la entidad `Usuario`                            |
| `TopicoRepository`       | Capa de acceso a datos para la entidad `Topico`                             |

### 🔹 Controladores
| **Tipo**       | **Función**                                                                 |
|----------------|-----------------------------------------------------------------------------|
| `Controller`   | Clases con endpoints expuestos (REST API)                                   |

## 🧑‍💻 Autor

**Pedro Montes**  
📧 [pedromontesprmd@gmail.com](mailto:pedromontesprmd@gmail.com)  
💼 Proyecto académico de práctica con *Spring Boot* y *Alura*

---

## 📝 Notas importantes

### Pruebas de la API
- Se recomienda probar los endpoints con:
  - <img src="https://img.icons8.com/nolan/30/postman.png" width="20" alt="Postman"/> [Postman](https://www.postman.com/)
  - <img src="https://img.icons8.com/nolan/30/insomnia.png" width="20" alt="Insomnia"/> [Insomnia](https://insomnia.rest/)



## 📦 Dependencias principales en `pom.xml`

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Flyway -->
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>

    <!-- Swagger / OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.0.4</version>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- ModelMapper -->
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.1.1</version>
    </dependency>
</dependencies>
