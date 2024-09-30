# API RESTful de Gestión de Usuarios

## Descripción

Esta API permite la creación de usuarios y la gestión de sus datos. Los usuarios pueden ser registrados junto con un listado de teléfonos. El sistema valida el formato del correo y la contraseña mediante expresiones regulares. Además, genera un token JWT para cada usuario registrado, el cual es persistido junto con la información del usuario.

## Características

- Registra un usuario con los campos `name`, `email`, `password` y una lista de phone.
- Valida el correo electrónico con una expresión regular.
- Valida la contraseña con una expresión regular configurada.
- Genera un `token` JWT para cada usuario registrado.
- Persistencia de datos en memoria con H2.
- Control de errores mediante mensajes JSON.

## Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **Java 11** o superior
- **Maven 3.6.3** o superior
- 
## Tecnologías Utilizadas

- **Spring Boot 2.7.8**
- **JPA/Hibernate** para la persistencia de datos
- **H2** como base de datos en memoria
- **JWT (JSON Web Token)** para la generación de tokens de autenticación
- **BCrypt** para el cifrado de contraseñas
- **Lombok** para simplificar la escritura de código

## Instalación y Configuración

### Clonar el Repositorio

```bash
git clone https://github.com/jerry-rodrigo/evaluacionIntegracionJava

```

## Construcción del Proyecto
- Para construir el proyecto usando Maven, ejecuta el siguiente comando:
mvn clean install

## Ejecución del Proyecto
- Para ejecutar la aplicación, utiliza el siguiente comando:
mvn spring-boot:run

## La aplicación se ejecutará en:
http://localhost:8080

## Accede a la consola de H2 para inspeccionar los datos:
- URL de la consola: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:desafioTecnicoDb
- Usuario: sa
- Contraseña: sasa

## API Endpoints
1. Registro de Usuario
 ## URL: /api/v1/users/register
 ## Método: POST
 ## Descripción: Registra un nuevo usuario con nombre, correo, contraseña y una lista de teléfonos.
 ## Formato de solicitud:

{
"name": "Jerry Támara",
"email": "jerry@gmail.com",
"password": "Passw0rd@123",
"phones": [
{
"number": "981383709",
"citycode": "51",
"contrycode": "57"
}
]
}


 ## Formato de respuesta exitosa (201 Created):
{
"id": "a0fe8dd1-aa70-4a89-86a8-1408bf9e97b7",
"name": "Jerry Támara",
"email": "jerry@gmail.com",
"password": "$2a$10$gcJFvV1YkJ6FfvYM8SdlIu5bQCsydt.UDllpWAS1hJKrYaeOVF.AK",
"phones": [
{
"number": "981383709",
"cityCode": "51",
"contryCode": "57"
}
],
"created": "2024-09-25T16:37:25.3678296",
"modified": "2024-09-25T16:37:25.3678296",
"lastLogin": "2024-09-25T16:37:25.3678296",
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqZXJyeUBnbWFpbC5jb20iLCJqdGkiOiJiMzZmN2RhOS1mYTI3LTQwYjQtYmQ2Ni1lODAzMWEwOTA1MGUiLCJpYXQiOjE3MjczMDAyNDV9.VD-EexLjWAyZ8Keth9zDHtgwkclJ2vGx0PcVD3MzLIO3sEFskhKndnr1UqrM3sM3z-39xPpO-nA2fKqx9HS4MQ",
"isActive": true
}

 ## Errores posibles:

 - Correo ya registrado:

{
"mensaje": "El correo ya registrado"
}

 ## Formato de correo inválido:

{
"mensaje": "El correo no tiene un formato válido"
}

 ## Contraseña no válida (no cumple con la expresión regular):

{
"mensaje": "La contraseña debe tener al menos 8 caracteres, incluir al menos una letra mayúscula, una letra minúscula, un número y un carácter especial."
}

 ## Obtener Usuario por ID
 ## Descripción: Obtiene un usuario por su ID.
- URL: /api/v1/users/{id}
- Método HTTP: GET
- Formato de respuesta exitosa (200 OK):

{
"id": "a0fe8dd1-aa70-4a89-86a8-1408bf9e97b7",
"name": "Jerry Támara",
"email": "jerry@gmail.com",
"password": "$2a$10$gcJFvV1YkJ6FfvYM8SdlIu5bQCsydt.UDllpWAS1hJKrYaeOVF.AK",
"phones": [
{
"number": "981383709",
"cityCode": "51",
"contryCode": "57"
}
],
"created": "2024-09-25T16:37:25.3678296",
"modified": "2024-09-25T16:37:25.3678296",
"lastLogin": "2024-09-25T16:37:25.3678296",
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqZXJyeUBnbWFpbC5jb20iLCJqdGkiOiJiMzZmN2RhOS1mYTI3LTQwYjQtYmQ2Ni1lODAzMWEwOTA1MGUiLCJpYXQiOjE3MjczMDAyNDV9.VD-EexLjWAyZ8Keth9zDHtgwkclJ2vGx0PcVD3MzLIO3sEFskhKndnr1UqrM3sM3z-39xPpO-nA2fKqx9HS4MQ",
"isActive": true
}

 ## Actualizar Usuario
 - Descripción: Actualiza la información de un usuario existente por su ID.

 - URL: /api/v1/users/{id}
 - Método HTTP: PUT
 - Formato de solicitud:

{
"name": "Juan Rodriguez Updated",
"email": "juan@gmail.com",
"password": "UpdatedPassword1@",
"phones": [
{
"number": "7654321",
"citycode": "2",
"contrycode": "57"
}
]
}


 ## Eliminar Usuario
 - Descripción: Elimina un usuario por su ID.
 - URL: /api/v1/users/{id}
 - Método HTTP: DELETE
 - Respuesta exitosa (204 No Content):
 - No retorna contenido.


## Pruebas
- Puedes probar los endpoints usando Postman, cURL o cualquier cliente HTTP de tu preferencia. A continuación, un ejemplo usando cURL:

 ## Registro de un usuario:

curl -X POST http://localhost:8080/api/v1/users/register \
-H "Content-Type: application/json" \
-d '{
"name": "Juan Rodriguez",
"email": "juan@rodriguez.com",
"password": "Hunter2@",
"phones": [
{
"number": "1234567",
"citycode": "1",
"contrycode": "57"
}
]
}'

## DIAGRAMA
![img.png](img.png)

![img_1.png](img_1.png)

## Descripción de Componentes
## API RESTful (Spring Boot):
Utilizamos Spring Boot para crear la API que manejará las solicitudes HTTP.

## Controladores:
UserController: Este controlador expone los endpoints de la API, como el registro de usuarios. Se encarga de recibir las solicitudes y devolver respuestas adecuadas.

## Servicios:
UserService: Contiene la lógica de negocio para la gestión de usuarios. Se encarga de la validación de datos, la creación de usuarios y la generación de tokens.

## Repositorios:
UserRepository: Interfaz que extiende JpaRepository para interactuar con la base de datos y realizar operaciones CRUD en la entidad User.

## Base de Datos:
H2 Database: Base de datos en memoria que se utiliza para almacenar los datos de los usuarios durante el desarrollo y las pruebas.

## Validación y Configuración:
Se implementan expresiones regulares para validar el formato del correo y la contraseña. Estos valores pueden ser configurables a través de archivos de propiedades.

## Seguridad JWT:
Se utiliza JWT (JSON Web Tokens) para la generación de tokens de acceso que autentican a los usuarios en la API.

## Manejo de Errores:
Todos los errores se manejan de manera centralizada y se devuelven en el formato JSON especificado: