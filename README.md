LiterAlura

LiterAlura es una aplicación de consola desarrollada en Java con Spring Boot que consume la API pública Gutendex para buscar información de libros del proyecto Gutenberg y almacenar los resultados en una base de datos PostgreSQL utilizando Spring Data JPA.

El proyecto forma parte del challenge de backend de Alura y tiene como objetivo practicar el consumo de APIs externas, el manejo de JSON, la persistencia de datos con JPA y la construcción de aplicaciones backend estructuradas.

Tecnologías utilizadas

Java 17
Spring Boot
Spring Data JPA
PostgreSQL
Jackson
Maven
IntelliJ IDEA
API Gutendex

Descripción de los paquetes:

api
Contiene las clases responsables de consumir la API externa y convertir los datos JSON a objetos Java.

dto
Define los Data Transfer Objects utilizados para mapear la respuesta JSON de la API Gutendex.

model
Contiene las entidades JPA que representan las tablas de la base de datos.

repository
Define las interfaces que extienden JpaRepository para interactuar con la base de datos.

principal
Contiene la lógica principal de interacción con el usuario a través de un menú en consola.

Funcionalidades

La aplicación permite realizar las siguientes operaciones:

Buscar libro por título utilizando la API Gutendex.

Guardar libros y autores en la base de datos PostgreSQL.

Listar todos los libros registrados.

Listar autores registrados.

Listar autores vivos en un año determinado.

Listar libros por idioma.

Evitar duplicados al guardar libros.

API utilizada

La aplicación consume la API pública Gutendex.

Documentación oficial:
https://gutendex.com/

Ejemplo de búsqueda:

https://gutendex.com/books/?search=pride+and+prejudice

La API devuelve los datos en formato JSON y estos se transforman a objetos Java utilizando la biblioteca Jackson.

Base de datos

Se utiliza PostgreSQL para la persistencia de datos.

Configuración típica en application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
Cómo ejecutar el proyecto

Clonar el repositorio

git clone https://github.com/lauv108/literalura.git

Entrar al proyecto

cd literalura

Configurar PostgreSQL y crear la base de datos

CREATE DATABASE literalura;

Configurar credenciales en application.properties.

Ejecutar la aplicación desde IntelliJ o con Maven.

Menú de la aplicación

Al ejecutar la aplicación se muestra un menú interactivo en consola que permite al usuario:

Buscar libros por título.

Listar libros registrados.

Consultar autores registrados.

Buscar autores vivos en un año determinado.

Consultar libros por idioma.

Aprendizajes del proyecto

Consumo de APIs REST en Java.

Uso de HttpClient para realizar solicitudes HTTP.

Deserialización de JSON con Jackson.

Uso de Streams y Lambdas.

Persistencia de datos con Spring Data JPA.

Modelado de entidades con ORM.

Consultas derivadas en Spring Data JPA.

Manejo de excepciones.

Diseño de aplicaciones backend en capas.
