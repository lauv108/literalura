package com.alura.literalura.principal;

import com.alura.literalura.api.ConsumoAPI;
import com.alura.literalura.api.ConvierteDatos;
import com.alura.literalura.dto.DatosAutor;
import com.alura.literalura.dto.DatosGutendex;
import com.alura.literalura.dto.DatosLibro;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    
                    ===== LiterAlura =====
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ======================
                    Elige una opción:
                    """;

            System.out.println(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingresa un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();

        String url = URL_BASE + nombreLibro.replace(" ", "+");
        var json = consumoAPI.obtenerDatos(url);

        DatosGutendex datos = conversor.obtenerDatos(json, DatosGutendex.class);

        if (datos.resultados() == null || datos.resultados().isEmpty()) {
            System.out.println("Libro no encontrado");
            return;
        }

        DatosLibro primerLibro = datos.resultados().get(0);

        if (libroRepository.findByTitulo(primerLibro.titulo()).isPresent()) {
            System.out.println("No se puede insertar el libro porque ya está registrado.");
            return;
        }

        DatosAutor datosAutor = null;
        if (primerLibro.autores() != null && !primerLibro.autores().isEmpty()) {
            datosAutor = primerLibro.autores().get(0);
        }

        String idioma = "Desconocido";
        if (primerLibro.idiomas() != null && !primerLibro.idiomas().isEmpty()) {
            idioma = primerLibro.idiomas().get(0);
        }

        Autor autor;

        if (datosAutor != null) {
            var autorExistente = autorRepository.findByNombre(datosAutor.nombre());

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor = new Autor(
                        datosAutor.nombre(),
                        datosAutor.anioNacimiento(),
                        datosAutor.anioFallecimiento()
                );
                autorRepository.save(autor);
            }
        } else {
            var autorExistente = autorRepository.findByNombre("Autor desconocido");

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor = new Autor("Autor desconocido", null, null);
                autorRepository.save(autor);
            }
        }

        Libro libro = new Libro(
                primerLibro.titulo(),
                idioma,
                primerLibro.numeroDescargas(),
                autor
        );

        libroRepository.save(libro);

        System.out.println("""
                
                ********** LIBRO GUARDADO **********
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %.0f
                ***********************************
                """.formatted(
                libro.getTitulo(),
                autor.getNombre(),
                libro.getIdioma(),
                libro.getNumeroDescargas()
        ));
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\n===== LIBROS REGISTRADOS =====");
        libros.forEach(libro -> System.out.println("""
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %.0f
                ------------------------------
                """.formatted(
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getIdioma(),
                libro.getNumeroDescargas()
        )));
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        System.out.println("\n===== AUTORES REGISTRADOS =====");

        autores.forEach(autor -> System.out.println("""
            Autor: %s
            Año de nacimiento: %s
            Año de fallecimiento: %s
            -----------------------------
            """.formatted(
                autor.getNombre(),
                autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "Desconocido",
                autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Vivo o desconocido"
        )));
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            
            Ingresa el idioma para buscar los libros:
            en - Inglés
            es - Español
            fr - Francés
            pt - Portugués
            """);

        String idioma = teclado.nextLine().toLowerCase().trim();

        var libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
            return;
        }

        System.out.println("\n===== LIBROS EN IDIOMA " + idioma.toUpperCase() + " =====");

        libros.forEach(libro -> System.out.println("""
            Título: %s
            Autor: %s
            Idioma: %s
            Número de descargas: %.0f
            ------------------------------
            """.formatted(
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getIdioma(),
                libro.getNumeroDescargas()
        )));
    }
    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingresa el año para consultar autores vivos:");
        Integer anio;

        try {
            anio = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un año válido.");
            return;
        }

        var autoresVivos = autorRepository
                .findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);

        var autoresSinFallecimiento = autorRepository
                .findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(anio);

        autoresVivos.addAll(autoresSinFallecimiento);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            return;
        }

        System.out.println("\n===== AUTORES VIVOS EN EL AÑO " + anio + " =====");

        autoresVivos.forEach(autor -> System.out.println("""
            Autor: %s
            Año de nacimiento: %s
            Año de fallecimiento: %s
            -----------------------------
            """.formatted(
                autor.getNombre(),
                autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "Desconocido",
                autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Vivo o desconocido"
        )));
    }

}