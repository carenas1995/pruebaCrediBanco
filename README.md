# 💳 Prueba Técnica - Backend CrediBanco

API REST desarrollada en Java con Spring Boot para la gestión de tarjetas de crédito y débito, y el procesamiento de transacciones financieras.

El sistema permite generar y enrolar tarjetas, consultar y recargar saldos, procesar compras y realizar anulaciones de transacciones.

---

## 🚀 Tecnologías utilizadas

* **Lenguaje:** Java 17 (JDK Adoptium).
* **Framework:** Spring Boot 3.2.5.
* **Seguridad:** Spring Security + JWT (JSON Web Tokens).
* **Persistencia:** Spring Data JPA / Hibernate.
* **Base de datos:** MySQL 8.
* **Herramienta de construcción:** Gradle.
* **Librerías auxiliares:** Lombok y JJWT (`io.jsonwebtoken`).

---

## 🗄️ Modelo de base de datos

El script SQL `schema.sql` define las siguientes entidades:

| Tabla          | Descripción                                                                                        |
| -------------- | -------------------------------------------------------------------------------------------------- |
| `users`        | Gestión de usuarios, autenticación y control de acceso mediante roles `ADMIN` y `USER`.            |
| `cards`        | Almacenamiento de tarjetas, incluyendo número de 16 dígitos, fecha de vencimiento, estado y saldo. |
| `transactions` | Registro de compras y seguimiento del estado de las transacciones y sus anulaciones.               |

---

## 🛠️ Configuración e instalación

### 1. Requisitos previos

Antes de ejecutar el proyecto, asegúrate de contar con:

* JDK 17 o superior.
* MySQL 8 instalado y en ejecución en el puerto `3306`.
* Gradle o el Gradle Wrapper incluido en el proyecto.
* IntelliJ IDEA u otro IDE compatible con Java (opcional).

### 2. Configuración de la base de datos

Crea la base de datos en MySQL ejecutando el siguiente script:

```sql
CREATE DATABASE IF NOT EXISTS credibanco_db;
```

Posteriormente, ejecuta el script `schema.sql` para crear las tablas necesarias.

### 3. Configuración de la aplicación

Abre el archivo `src/main/resources/application.properties` y configura las credenciales de conexión a tu base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/credibanco_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

**Importante:** Reemplaza `tu_contraseña` por la contraseña de tu servidor MySQL.

### 4. Ejecución del proyecto

Desde una terminal ubicada en la raíz del proyecto, ejecuta:

**Windows:**

```bash
gradlew.bat bootRun
```

**Linux / macOS:**

```bash
./gradlew bootRun
```

También puedes ejecutar la aplicación directamente desde IntelliJ IDEA.

Una vez iniciada, la API estará disponible en:

`http://localhost:8080`

---

## 🔐 Autenticación y seguridad JWT

La API utiliza autenticación basada en **JSON Web Tokens (JWT)** para proteger los endpoints.

A excepción del endpoint de inicio de sesión (`/auth/login`), las solicitudes requieren un token de acceso válido en la cabecera HTTP:

```http
Authorization: Bearer <TOKEN_JWT>
```

### Credenciales de prueba

| Campo      | Valor                  |
| ---------- | ---------------------- |
| Email      | `admin@credibanco.com` |
| Contraseña | `admin123`             |

Estas credenciales corresponden al usuario administrador de prueba, siempre que haya sido creado o configurado en la base de datos.

---

## 📋 Documentación de endpoints

### 1. Autenticación (`/auth`)

| Método | Endpoint      | Descripción                                    |
| ------ | ------------- | ---------------------------------------------- |
| `POST` | `/auth/login` | Inicia sesión y genera un token JWT de acceso. |

### 2. Gestión de tarjetas (`/card`)

| Método   | Endpoint                     | Descripción                                                                                                              |
| -------- | ---------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| `GET`    | `/card/{productId}/generate` | Genera un número de tarjeta de 16 dígitos, inicialmente inactiva, utilizando los 6 dígitos del `productId` como prefijo. |
| `POST`   | `/card/enroll`               | Activa o enrola una tarjeta para habilitar las transacciones.                                                            |
| `DELETE` | `/card/{cardId}`             | Bloquea una tarjeta existente.                                                                                           |
| `POST`   | `/card/balance`              | Recarga el saldo de una tarjeta.                                                                                         |
| `GET`    | `/card/balance/{cardId}`     | Consulta el saldo actual de una tarjeta.                                                                                 |

### 3. Procesamiento de transacciones (`/transaction`)

| Método | Endpoint                       | Descripción                                                                                        |
| ------ | ------------------------------ | -------------------------------------------------------------------------------------------------- |
| `POST` | `/transaction/purchase`        | Procesa una compra y debita el importe del saldo de la tarjeta activa.                             |
| `GET`  | `/transaction/{transactionId}` | Consulta el estado y los detalles de una transacción.                                              |
| `POST` | `/transaction/anull`           | Anula una transacción realizada durante las últimas 24 horas y reintegra el saldo correspondiente. |

---

## 🧪 Pruebas con Postman

El proyecto incluye la colección de Postman `CrediBanco_API.postman_collection.json`, que permite probar los endpoints de la API.

### Pasos para ejecutar las pruebas

1. Abre Postman.
2. Importa el archivo `CrediBanco_API.postman_collection.json` mediante la opción **File → Import**.
3. Ejecuta la solicitud `01. Auth - Login` para autenticarte y obtener el token JWT.
4. Verifica que el token se haya guardado correctamente en las variables de la colección.
5. Ejecuta las demás solicitudes en el orden correspondiente para probar las funcionalidades de la API.

### Funcionalidades que se pueden validar

* Inicio de sesión y generación del token JWT.
* Generación y enrolamiento de tarjetas.
* Bloqueo de tarjetas.
* Consulta y recarga de saldo.
* Procesamiento de compras.
* Consulta del estado de las transacciones.
* Anulación de compras y reintegro del saldo.

---

## 📌 Consideraciones

* Las tarjetas deben estar activas para realizar operaciones que lo requieran.
* Las compras deben contar con saldo suficiente para ser procesadas.
* Las anulaciones están sujetas a la ventana de tiempo de 24 horas establecida por el sistema.
* Las operaciones financieras deben mantener la consistencia del saldo y del registro de transacciones.

---

## 👨‍💻 Autor

**Carlos Eduardo Arenas Mendieta**

Desarrollador Full-Stack | Java | Spring Boot | Angular | .NET
