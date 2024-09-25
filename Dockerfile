# Usar una imagen base de Java
FROM openjdk:11

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de tu aplicación al contenedor
COPY target/desafioTecnico-0.0.1-SNAPSHOT.jar /app

# Exponer el puerto en el que se ejecutará tu aplicación
EXPOSE 8080

ADD ./target/desafioTecnico-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]