# Back Xentrix API

API REST desarrollada con Java y Spring Boot para la gestión de eventos, con persistencia de datos en PostgreSQL.

El módulo `events` permite consultar todos los eventos registrados, consultar un evento por identificador y crear nuevos eventos.

## Tabla de contenido

- [Descripción general](#descripción-general)
- [Tecnologías](#tecnologías)
- [Arquitectura del módulo](#arquitectura-del-módulo)
- [Modelo de datos](#modelo-de-datos)
- [Requisitos previos](#requisitos-previos)
- [Configuración de PostgreSQL](#configuración-de-postgresql)
- [Configuración de la aplicación](#configuración-de-la-aplicación)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Endpoints](#endpoints)
- [Ejemplos de uso](#ejemplos-de-uso)
- [Manejo de errores](#manejo-de-errores)
- [Consideraciones técnicas](#consideraciones-técnicas)

## Descripción general

Back Xentrix expone servicios HTTP para administrar eventos mediante una API REST.

El módulo está organizado en capas para separar las responsabilidades:

- **Controller:** recibe las solicitudes HTTP y construye las respuestas.
- **Service:** contiene las reglas de negocio y valida los datos requeridos para crear eventos.
- **Adapter:** comunica la lógica de negocio con la capa de persistencia.
- **Repository:** proporciona las operaciones de acceso a la base de datos mediante Spring Data JPA.
- **Entity:** representa la tabla `events` de PostgreSQL.
- **DTO:** define los datos que recibe la API para crear eventos.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java | Lenguaje de programación |
| Spring Boot | Framework de la aplicación |
| Spring Web | Construcción de la API REST |
| Spring Data JPA | Acceso a datos |
| Hibernate | Implementación ORM, si está incluida en las dependencias |
| PostgreSQL | Base de datos relacional |
| Maven | Gestión de dependencias y construcción, si el proyecto utiliza Maven |
| Jackson | Conversión de objetos a JSON mediante la configuración habitual de Spring Boot |

La versión exacta de Java, Spring Boot y las dependencias instaladas debe verificarse en el proyecto.

## Arquitectura del módulo

```text
api_xentrix/
└── events/
    ├── EventsController.java
    ├── EventsService.java
    └── adapters/
        ├── EventsAdapter.java
        ├── bd1/
        │   ├── Events.java
        │   └── EventsRepository.java
        └── payloads/
            └── EventDto.java
```

### Responsabilidades de las clases

| Clase | Responsabilidad |
|---|---|
| `EventsController` | Expone los endpoints HTTP bajo `/local/events`. |
| `EventsService` | Valida los campos requeridos y coordina las operaciones de eventos. |
| `EventsAdapter` | Ejecuta operaciones de consulta y persistencia mediante el repositorio. |
| `EventsRepository` | Accede a los registros de la tabla `events`. |
| `Events` | Entidad JPA que representa un evento en la base de datos. |
| `EventDto` | Objeto de transferencia de datos utilizado en la creación de eventos. |

## Modelo de datos

### Entidad `Events`

La entidad `Events` representa la tabla `events` en PostgreSQL.

Está identificada mediante `@Entity` y `@Table(name = "events")`. Su clave primaria es `id`, generada automáticamente mediante `GenerationType.IDENTITY`.

| Campo Java | Tipo Java | Columna PostgreSQL | Descripción |
|---|---|---|---|
| `id` | `Integer` | `id` | Identificador único del evento. |
| `name` | `String` | `name` | Nombre del evento. |
| `type` | `String` | `type` | Tipo o categoría del evento. |
| `capacity` | `Integer` | `capacity` | Capacidad máxima del evento. |
| `location` | `String` | `location` | Ubicación del evento. |
| `eventOpeningDate` | `Timestamp` | `event_opening_date` | Fecha y hora de apertura del evento. |
| `eventClosingDate` | `Timestamp` | `event_closing_date` | Fecha y hora de cierre del evento. |

Los nombres de las columnas están definidos explícitamente mediante `@Column`.

**Consideraciones:**

- El identificador se genera al guardar el registro.
- `eventOpeningDate` y `eventClosingDate` existen en la entidad, pero actualmente no se asignan durante la creación implementada en `EventsService`.
- No se han definido restricciones `nullable`, longitudes ni reglas de negocio adicionales en las anotaciones mostradas.
- La creación automática de tablas depende de la configuración `spring.jpa.hibernate.ddl-auto`. En producción es preferible administrar cambios del esquema mediante migraciones.

### DTO `EventDto`

`EventDto` representa los datos que el cliente debe enviar para crear un evento.

| Campo | Tipo | Requerido para crear |
|---|---|---|
| `name` | `String` | Sí |
| `type` | `String` | Sí |
| `capacity` | `Integer` | Sí |
| `location` | `String` | Sí |

La validación manual de `EventsService.create()` comprueba que los cuatro campos no sean `null`.

Actualmente, esa comprobación no impide que `name`, `type` o `location` contengan cadenas vacías o espacios. Además, las anotaciones de validación del DTO no se ejecutan automáticamente en el controlador porque el parámetro `@RequestBody` no utiliza `@Valid`.

Se recomienda corregir la validación de `capacity` y activar la validación del DTO; los detalles están en [Consideraciones técnicas](#consideraciones-técnicas).

## Requisitos previos

Antes de ejecutar la aplicación, verifica que el entorno tenga:

1. Java en una versión compatible con el proyecto.
2. PostgreSQL instalado y en ejecución.
3. Una base de datos llamada `dev_centrix`.
4. Maven instalado si se ejecuta desde la terminal y no se dispone del Maven Wrapper.
5. Las dependencias necesarias declaradas en `pom.xml`.

Para comprobar las herramientas:

```bash
java -version
mvn -version
psql --version
```

Si el proyecto incluye `mvnw` o `mvnw.cmd`, puedes utilizar el Maven Wrapper sin instalar Maven globalmente.

## Configuración de PostgreSQL

### 1. Crear la base de datos

Abre `psql` o una herramienta como pgAdmin y ejecuta:

```sql
CREATE DATABASE dev_centrix;
```

Si la base de datos ya existe, no es necesario crearla de nuevo.

### 2. Configurar el usuario

Utiliza un usuario de PostgreSQL con permisos para conectarse a `dev_centrix` y leer y modificar las tablas requeridas. Para desarrollo, puedes usar un usuario específico con una contraseña propia y permisos mínimos. No utilices credenciales de producción en el entorno local.

### 3. Crear o verificar la tabla `events`

Si la aplicación tiene habilitada la generación automática de tablas mediante Hibernate, la tabla puede generarse a partir de la entidad `Events`, dependiendo de la configuración JPA.

Si prefieres crearla manualmente, puedes utilizar este esquema inicial:

```sql
CREATE TABLE IF NOT EXISTS events (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    capacity INTEGER,
    location VARCHAR(255),
    event_opening_date TIMESTAMP,
    event_closing_date TIMESTAMP
);
```

Este esquema corresponde a los campos y tipos de la entidad mostrada. Las restricciones de obligatoriedad deben añadirse cuando se definan las reglas definitivas del modelo.

Si utilizas Flyway, Liquibase u otro mecanismo de migraciones, administra el esquema mediante ese mecanismo en lugar de mantener varias fuentes de creación de tablas.

## Configuración de la aplicación

Configura la conexión a PostgreSQL en `src/main/resources/application.properties`.

Ejemplo para desarrollo local:

```properties
# Configuración de la aplicación
server.port=8080

# Conexión PostgreSQL
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/dev_centrix}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Este ejemplo supone que PostgreSQL está disponible en `localhost:5432` y que existe un usuario con permisos sobre `dev_centrix`.

Antes de ejecutar la aplicación, define las variables de entorno `DB_USERNAME` y `DB_PASSWORD`. El usuario predeterminado del ejemplo es `postgres`, pero debes cambiarlo si utilizas otro usuario.

La dependencia del controlador PostgreSQL debe estar presente en `pom.xml`. Si aún no existe, para un proyecto Maven puedes agregar:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Importante:** `spring.jpa.hibernate.ddl-auto=update` es práctico durante el desarrollo, pero no debe utilizarse como estrategia de migración en producción. En ese entorno, configura el esquema mediante migraciones y utiliza credenciales seguras.

## Instalación y ejecución

### 1. Obtener el código fuente

Clona el repositorio o abre el proyecto existente en tu IDE.

```bash
git clone <URL_DEL_REPOSITORIO>
cd <DIRECTORIO_DEL_PROYECTO>
```

Reemplaza los valores entre `<` y `>` por los datos reales del repositorio.

### 2. Configurar las credenciales

En Linux o macOS:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/dev_centrix
export DB_USERNAME=postgres
export DB_PASSWORD='tu_contraseña'
```

En PowerShell:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/dev_centrix"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "tu_contraseña"
```

No publiques las credenciales ni las incluyas en el repositorio.

### 3. Compilar el proyecto

Desde el directorio que contiene `pom.xml`:

```bash
mvn clean package
```

Para compilar y ejecutar las pruebas, si están configuradas:

```bash
mvn clean verify
```

### 4. Ejecutar la aplicación

Con Maven:

```bash
mvn spring-boot:run
```

En Windows, si el proyecto dispone del Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

También puedes ejecutar el JAR generado, sustituyendo el nombre por el archivo real:

```bash
java -jar target/<NOMBRE_DEL_ARCHIVO>.jar
```

Si el arranque es exitoso, Spring Boot iniciará el servidor en el puerto configurado. Revisa los logs para confirmar que la conexión a PostgreSQL se estableció correctamente y que no existen errores al inicializar JPA.

### 5. URL base de la API

Los ejemplos de este README asumen la siguiente configuración:

```text
http://localhost:8080/back-xentrix/api/v1
```

Esta URL requiere que la aplicación tenga configurado el contexto `/back-xentrix/api/v1`, por ejemplo mediante `server.servlet.context-path` o una configuración equivalente.

Si el contexto no está configurado, las rutas pueden comenzar directamente por `/local/events`.

## Endpoints

El controlador `EventsController` define tres endpoints bajo el prefijo `/local/events`.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/local/events/getAll` | Consulta todos los eventos. |
| `GET` | `/local/events/get/{id}` | Consulta un evento por su identificador. |
| `POST` | `/local/events/create` | Crea un evento. |

### 1. Consultar todos los eventos

**Método:** `GET`

**Ruta:** `/local/events/getAll`

**Ejemplo:**

```http
GET /back-xentrix/api/v1/local/events/getAll
```

La operación utiliza `EventsAdapter.getAll()`, que llama a `eventsRepository.findAll()`.

Ejemplo ilustrativo de respuesta cuando existen registros:

```json
[
  {
    "id": 1,
    "name": "Concierto de rock",
    "type": "Concierto",
    "capacity": 500,
    "location": "Bogotá",
    "eventOpeningDate": null,
    "eventClosingDate": null
  }
]
```

La respuesta real dependerá de los datos almacenados y de la serialización configurada en la aplicación.

### 2. Consultar un evento por ID

**Método:** `GET`

**Ruta:** `/local/events/get/{id}`

| Parámetro | Tipo | Descripción |
|---|---|---|
| `id` | `Integer` | Identificador del evento. |

**Ejemplo:**

```http
GET /back-xentrix/api/v1/local/events/get/1
```

El adaptador utiliza `eventsRepository.findById(id)`, cuyo retorno es un `Optional<Events>` en Spring Data JPA.

La representación HTTP de ese resultado depende de la serialización configurada. Se recomienda desenvolver el `Optional` en el adaptador o servicio para devolver directamente la entidad o un resultado de ausencia controlado.

Si no existe el evento, la lógica actual del servicio intenta devolver el texto:

```text
evento no encontrado
```

Sin embargo, el chequeo `eventResp == null` no detecta un `Optional.empty()`. Por eso, el comportamiento de evento inexistente debe corregirse antes de considerarlo un contrato definitivo.

### 3. Crear un evento

**Método:** `POST`

**Ruta:** `/local/events/create`

**Encabezados:**

```http
Content-Type: application/json
```

**Cuerpo de la solicitud:**

```json
{
  "name": "Concierto de rock",
  "type": "Concierto",
  "capacity": 500,
  "location": "Bogotá"
}
```

Los cuatro atributos son requeridos por la validación manual de `EventsService.create()`.

**Proceso de creación:**

1. El controlador recibe el JSON y lo convierte en `EventDto`.
2. El servicio verifica que `name`, `type`, `capacity` y `location` no sean `null`.
3. Se construye una instancia de `Events` con esos cuatro campos.
4. El adaptador persiste la entidad mediante `eventsRepository.save(event)`.
5. Se obtiene el identificador generado por la base de datos.
6. El servicio devuelve un mensaje de confirmación.

**Ejemplo de respuesta actual:**

```text
fue creado de forma exitosa el evento con id 1
```

El identificador del ejemplo es ilustrativo.

Actualmente, la operación de creación no asigna `eventOpeningDate` ni `eventClosingDate`, por lo que esos atributos quedan sin valor, salvo que otra capa o la base de datos los complete.

## Ejemplos de uso

Los siguientes comandos utilizan la URL base asumida en este README. Ajusta la dirección si tu aplicación tiene otro puerto o contexto.

### Consultar todos los eventos

```bash
curl -X GET \
  "http://localhost:8080/back-xentrix/api/v1/local/events/getAll"
```

### Consultar un evento

```bash
curl -X GET \
  "http://localhost:8080/back-xentrix/api/v1/local/events/get/1"
```

### Crear un evento

```bash
curl -X POST \
  "http://localhost:8080/back-xentrix/api/v1/local/events/create" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Concierto de rock",
    "type": "Concierto",
    "capacity": 500,
    "location": "Bogotá"
  }'
```

Estos ejemplos pueden ejecutarse desde una terminal o adaptarse a Postman.

## Manejo de errores

El módulo captura excepciones en las capas de servicio y adaptador, y utiliza `ErrorService` para representar información de error.

Los mensajes definidos actualmente incluyen:

- `Error consultando todos los eventos`
- `Error consultando el evento con uid {id}`
- `Error consultando el evento con id {id}`
- `Error creando el evento`
- `Datos inconsistentes para crear el evento`

El texto exacto depende de la ejecución y de las excepciones producidas.

### Comportamiento actual

El servicio captura las excepciones y devuelve un objeto `ErrorService`. Por tanto, en muchos casos el controlador recibe ese objeto como un resultado normal y responde con `200 OK`.

Esto dificulta que los consumidores de la API identifiquen errores mediante el código HTTP.

### Códigos recomendados

| Situación | Código HTTP recomendado |
|---|---|
| Consulta exitosa | `200 OK` |
| Evento creado | `201 Created` |
| Evento inexistente | `404 Not Found` |
| Datos inválidos | `400 Bad Request` |
| Error inesperado | `500 Internal Server Error` |

Se recomienda centralizar el manejo de excepciones con `@RestControllerAdvice`, evitando exponer detalles internos de las excepciones en las respuestas públicas.

## Consideraciones técnicas

Antes de ampliar el módulo o desplegarlo en producción, se recomienda revisar los siguientes puntos.

### 1. Validación del DTO

`@NotBlank` está diseñado para cadenas de texto y no es apropiado para `Integer capacity`.

Se recomienda utilizar:

- `@NotBlank` para `name`, `type` y `location`.
- `@NotNull` para `capacity`.
- `@Positive` para `capacity` si la regla de negocio exige una capacidad mayor que cero.
- `@Valid` en el parámetro `@RequestBody` del controlador.

Además, `EventDto` necesita un constructor sin argumentos si se utiliza una configuración de Jackson que lo requiera para deserializar el JSON.

### 2. Manejo de `Optional` en la consulta por ID

`findById()` devuelve un `Optional<Events>`, no una entidad directamente ni `null`.

Se recomienda desenvolverlo con `orElseThrow()` o gestionar explícitamente la ausencia del registro para poder responder con `404 Not Found`.

### 3. Códigos HTTP y respuestas tipadas

Se recomienda reemplazar los retornos genéricos `Object` por modelos de respuesta definidos y usar códigos HTTP coherentes con el resultado.

En particular, `POST /create` debería devolver `201 Created` cuando se cree el evento y, preferiblemente, incluir el recurso creado o su identificador.

### 4. Inyección de dependencias

Los componentes utilizan actualmente `@Autowired` sobre atributos. La inyección por constructor facilita las pruebas unitarias, permite declarar las dependencias como `final` y hace más explícitos los requisitos de cada clase.

### 5. Fechas del evento

Los campos `eventOpeningDate` y `eventClosingDate` están presentes en la entidad, pero no forman parte del DTO actual.

Si deben ser configurables desde la API, será necesario añadirlos al DTO, definir su formato de entrada y validar que la fecha de cierre sea posterior a la fecha de apertura.

### 6. Seguridad y configuración

- Mantener las credenciales fuera del código fuente.
- No exponer mensajes internos de excepciones a los clientes.
- Configurar permisos mínimos para el usuario de PostgreSQL.
- Definir una estrategia de migraciones para los cambios del esquema.
- Incorporar pruebas unitarias y de integración para las operaciones del módulo.

## Estado actual del módulo

El módulo `events` implementa operaciones básicas de consulta y creación de eventos mediante Spring Web, una capa de servicio, un adaptador y un repositorio de persistencia.

La base de datos utilizada es PostgreSQL, con nombre `dev_centrix`. La configuración exacta del servidor, las versiones de las dependencias y el contexto de la aplicación deben confirmarse en los archivos de configuración del proyecto.
