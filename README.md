# Note Controller API

This API provides endpoints for managing Price.

## Endpoints

### Obtener precio por parámetros

- **URL:** `/api/price/listar`
- **Método:** GET
- **Descripción:** Recupera el precio basado en los parámetros proporcionados.
- **Autorización:** Requiere autenticación.
- **Roles:** Todos los usuarios autenticados pueden acceder a este endpoint.
- **Parámetros de solicitud:**
    - `brandId`: Identificador de la marca.
    - `productId`: Identificador del producto.
    - `applicationDate`: Fecha de aplicación.
- **Respuesta:** En caso de éxito, la respuesta contendrá el precio obtenido en el formato JSON. Si no se encuentra ningún precio, se devolverá una respuesta de error.

Ejemplo de respuesta exitosa:

```json
{
  "productId": 123,
  "brandId": 456,
  "priceList": 789,
  "startDate": "2022-01-01T00:00:00Z",
  "endDate": "2022-12-31T23:59:59Z",
  "price": 99.99,
  "currency": "USD"
}
```

## Authentication API

This API provides endpoints for user authentication and registration.

### Register User

- **URL:** `/api/auth/register`
- **Method:** POST
- **Description:** Registers a user with the "user" role.
- **Request Body:** The user registration data.
- **Response:** Returns a success message if the registration is successful.

### Register Admin

- **URL:** `/api/auth/registerAdm`
- **Method:** POST
- **Description:** Registers a user with the "admin" role.
- **Authorization:** Requires authentication.
- **Roles:** Only users with the "ADMIN" role can access this endpoint.
- **Request Body:** The admin registration data.
- **Response:** Returns a success message if the registration is successful.

### Login

- **URL:** `/api/auth/login`
- **Method:** POST
- **Description:** Authenticates a user and generates a JWT token.
- **Request Body:** The user login credentials.
- **Response:** Returns the JWT token if the authentication is successful.

## Security Configuration

This section provides information on the security configuration of the API.

### Security Configuration Class

- **Class:** `SecurityConfig`
- **Description:** Configures the security settings for the API.
- **Configuration:**
    - Disables CSRF protection.
    - Configures a custom authentication entry point for unauthorized access handling.
    - Configures session management to be stateless.
    - Defines authorization rules for different endpoints based on roles.
    - Adds a JWT authentication filter to the filter chain.
    - Allows access to Swagger UI and API documentation endpoints without authentication.

### Password Encoding

- **Class:** `PasswordEncoder`
- **Description:** Configures the password encoder used for user authentication.

### Authentication Manager

- **Class:** `AuthenticationManager`
- **Description:** Configures the authentication manager used for user authentication.

## Dependencies

This section lists the dependencies used in the API.

- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa`
- `jjwt-api`
- `jjwt-impl`
- `jjwt-jackson`
- `spring-boot-starter-validation`
- `spring-boot-configuration-processor`
- `spring-boot-starter-test` (for testing purposes)
- `h2-database` (for testing purposes)

Please refer to the project's `pom.xml` file for the specific versions of the dependencies used.

## How to Run

To run the API, follow these steps:

1. Ensure that you have Java and Maven installed on your system.
2. Clone the project repository.
3. Open a terminal and navigate to the project's root directory.
4. Run the command: `mvn spring-boot:run`.
5. The API will start running# Note Controller API

This API provides endpoints for managing notes.


Aquí tienes los pasos para generar un JAR, construir un contenedor Docker y algunos ejemplos de comandos CURL para acceder a los endpoints:

## Generar un JAR y construir un contenedor Docker

1. Para generar un JAR, ejecuta el siguiente comando en la terminal:

   ```
   mvn clean package
   ```

   Esto generará un archivo JAR en la carpeta `target` del proyecto.

2. Para construir la imagen del contenedor Docker, ejecuta el siguiente comando:

   ```
   docker build -t desafiotecnico-esp:1.0 .
   ```

   Este comando construirá una imagen del contenedor con el nombre `desafiotecnico-esp` y la etiqueta `1.0`. Asegúrate de incluir el punto `.` al final del comando para indicar que el archivo Dockerfile se encuentra en el directorio actual.

3. Para iniciar el contenedor, ejecuta el siguiente comando:

   ```
   docker run -p 8080:8080 desafiotecnico-esp:1.0
   ```

   Este comando iniciará el contenedor y mapeará el puerto `8080` del contenedor al puerto `8080` de tu máquina local. Puedes cambiar el número de puerto según tus necesidades.

4. Para detener el contenedor, primero necesitas obtener el ID o el nombre del contenedor en ejecución. Ejecuta el siguiente comando para obtener una lista de todos los contenedores en ejecución:

   ```
   docker ps
   ```

   Busca el ID o el nombre del contenedor que deseas detener.

5. Una vez que hayas obtenido el ID o el nombre del contenedor, ejecuta el siguiente comando para detenerlo:

   ```
   docker stop <ID o nombre del contenedor>
   ```

   Reemplaza `<ID o nombre del contenedor>` con el ID o el nombre correspondiente.

## Ejemplos de comandos CURL para endpoints

Aquí tienes algunos ejemplos de comandos CURL para acceder a los endpoints de tu aplicación:

1. Registrar un administrador:

   ```
   curl --location --request POST 'http://localhost:8080/api/auth/registerAdm' \--header 'Content-Type: application/json' \--header 'Cookie: JSESSIONID=1A45F4DA74396A6EF362BCAAEEFDD778' \--data-raw '{ "username": "Jerry", "password": "123"}'
   ```

2. Realizamos el Login con el usuario administrador creado anteriormente:

   ```
   curl --location --request POST 'http://localhost:8080/api/auth/login' \--header 'Content-Type: application/json' \--header 'Cookie: JSESSIONID=1A45F4DA74396A6EF362BCAAEEFDD778' \--data-raw '{ "username": "Jerry","password": "123"}'
   ```

   Reemplaza `{id}` con el ID del registro que deseas obtener.


3. Consulta de información de PRICE:

   ```
   curl --location --request GET 'http://localhost:8080/price?brandId=1&productId=35455&applicationDate=2020-06-14-15%3A00%3A00' \--header 'Content-Type: application/json' \--header 'Cookie: JSESSIONID=1A45F4DA74396A6EF362BCAAEEFDD778' \--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKZXJyeSIsImlhdCI6MTcxMDM2MTAxNiwiZXhwIjoxNzEwMzYxMzE2fQ.VIscCH98YkUOvcavzTHf2dfsjQvrU4cLq7TziUDTIDSWkx_X7A4o9bJGNRYeC07jVJd8c2hLV-k_x0KPt34Szg' \--data '{ "title": "Prueba", "content": "Probando servicio"}'
   ```

   En esta solicitud, los parámetros incluidos son:

- `brandId`: El ID de la marca que deseas consultar.
- `productId`: El ID del producto que deseas consultar.
- `applicationDate`: La fecha de aplicación para la consulta.

Recuerda reemplazar `localhost:8080` con la dirección y el puerto correctos en caso de que tu aplicación se esté ejecutando en un entorno diferente.

¡Asegúrate de tener Docker y Maven instalados y configurados correctamente antes de ejecutar estos comandos!