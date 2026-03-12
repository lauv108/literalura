# LiterAlura

## Descripción

LiterAlura es una aplicación de consola desarrollada en Java utilizando Spring Boot.  
La aplicación consume la API pública de Gutendex para buscar información de libros disponibles en el catálogo de Project Gutenberg y almacenar los resultados en una base de datos PostgreSQL.

El sistema permite buscar libros por título, guardar su información en la base de datos y realizar diferentes consultas sobre libros y autores registrados.

Este proyecto fue desarrollado como parte del desafío **LiterAlura Challenge Java**.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Jackson
- Maven
- API Gutendex

---

## Arquitectura del proyecto

El proyecto sigue una estructura modular basada en buenas prácticas de desarrollo backend con Spring Boot.

```
src
 └─ main
     └─ java
         └─ com.alura.literalura
             ├─ api
             │   ├─ ConsumoAPI.java
             │   ├─ ConvierteDatos.java
             │   └─ IConvierteDatos.java
             │
             ├─ dto
             │   ├─ DatosAutor.java
             │   ├─ DatosGutendex.java
             │   └─ DatosLibro.java
             │
             ├─ model
             │   ├─ Autor.java
             │   └─ Libro.java
             │
             ├─ repository
             │   ├─ AutorRepository.java
             │   └─ LibroRepository.java
             │
             └─ principal
                 └─ Principal.java
```

### Descripción de los paquetes

**api**  
Contiene las clases responsables de consumir la API externa y convertir los datos JSON.

**dto**  
Clases utilizadas para mapear la respuesta JSON recibida desde la API Gutendex.

**model**  
Entidades JPA que representan las tablas en la base de datos.

**repository**  
Interfaces que extienden `JpaRepository` y permiten realizar operaciones de persistencia.

**principal**  
Contiene la lógica de interacción con el usuario a través de la consola.

---

## Funcionalidades

La aplicación ofrece las siguientes funcionalidades:

- Buscar libro por título (consumiendo la API Gutendex)
- Guardar libros en la base de datos
- Listar libros registrados
- Listar autores registrados
- Listar autores vivos en un año determinado
- Listar libros por idioma

---

## API utilizada

La aplicación consume la API pública de Gutendex.

Documentación de la API:

https://gutendex.com/

Ejemplo de consulta:

```
https://gutendex.com/books/?search=pride+and+prejudice
```

La respuesta se recibe en formato JSON y se convierte a objetos Java utilizando la biblioteca **Jackson**.

---

## Configuración del proyecto

Requisitos necesarios para ejecutar el proyecto:

- Java JDK 17 o superior
- Maven
- PostgreSQL
- IntelliJ IDEA (recomendado)

---

## Configuración de la base de datos

En el archivo `application.properties` debes configurar la conexión a PostgreSQL.

Ejemplo:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
```

Asegúrate de que la base de datos exista antes de ejecutar la aplicación.

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio

```
git clone https://github.com/lauv108/literalura.git
```

2. Entrar al directorio del proyecto

```
cd literalura
```

3. Compilar el proyecto con Maven

```
mvn clean install
```

4. Ejecutar la aplicación

Desde IntelliJ IDEA ejecuta la clase:

```
LiteraluraApplication
```

---

## Ejemplo de uso

Al iniciar la aplicación se mostrará un menú en consola que permite al usuario interactuar con el sistema.

Ejemplo de búsqueda de libro:

```
Escribe el nombre del libro que deseas buscar:
Pride and Prejudice
```

Resultado:

```
Libro encontrado

Título: Pride and Prejudice
Autor: Jane Austen
Idioma: en
Número de descargas: 92780
```

---

## Autor

Proyecto desarrollado por:

Laura Velásquez
