# 🧠 Foro API

API REST para un foro de discusión construido con Java y Spring Boot. Permite a los usuarios registrarse, iniciar sesión, crear temas de discusión (tópicos), y ver usuarios y temas existentes. Esta versión no incluye control de roles (como `ADMIN` o `CLIENTE`), enfocándose en la estructura básica del foro y la autenticación JWT.

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

---
## 📡 Endpoints de la API
| Método | Endpoint                  | Descripción                                | Seguridad         |
|--------|---------------------------|--------------------------------------------|------------------|
| POST   | `/login`                  | Autenticación, devuelve token JWT          | Público          |
| POST   | `/usuarios`               | Registro de nuevo usuario                  | Público          |
| GET    | `/usuarios`               | Lista todos los usuarios                   | Requiere token   |
| POST   | `/topicos`                | Crear nuevo tópico                         | Requiere token   |
| GET    | `/topicos`                | Ver todos los tópicos                      | Requiere token   |
| GET    | `/topicos/{id}`           | Ver un tópico por su ID                    | Requiere token   |
| PUT    | `/topicos/{id}`           | Actualizar tópico por su creador           | Requiere token   |
| DELETE | `/topicos/{id}`           | Eliminar tópico por su creador             | Requiere token   |
| GET    | `/swagger-ui/index.html`  | Acceder a documentación Swagger            | Público          |
| GET    | `/v3/api-docs`            | JSON OpenAPI para Swagger                  | Público          |

## ⚙️ Seguridad

| **Componente**               | **Descripción**                                                                 |
|------------------------------|---------------------------------------------------------------------------------|
| **Autenticación**            | Vía JWT (JSON Web Tokens)                                                       |
| **Política de Sesión**       | Stateless (`SessionCreationPolicy.STATELESS`)                                   |
| **Filtro Personalizado**     | `SecurityFilter` (valida tokens en cada solicitud)                              |
| **Endpoints Públicos**       | `/login`, `/usuarios`, `/v3/api-docs/**`, `/swagger-ui.html`, `/swagger-ui/**`  |
| **Endpoints Protegidos**     | Cualquier otro endpoint requiere token JWT válido                               |

## 🧱 Estructura Básica del Proyecto

### 🔹 Entidades
| **Entidad**  | **Descripción**                                  |
|--------------|--------------------------------------------------|
| `Usuario`    | Modelo que representa a los usuarios del sistema |
| `Topico`     | Modelo para los temas de discusión               |

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

### Acceso a Swagger UI
```bash
http://localhost:8080/swagger-ui/index.html

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
