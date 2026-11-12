package controlador;

import modelo.Producto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ArchivoProductos {

    private final Path rutaArchivo;

    public ArchivoProductos() {
        rutaArchivo = Paths.get("productos.txt");
    }

    // Guardar todos los productos en el archivo
    public void guardarProductos(List<Producto> productos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                rutaArchivo, StandardCharsets.UTF_8)) {

            for (Producto producto : productos) {
                escritor.write(
                        producto.getId() + ";"
                        + limpiarTexto(producto.getNombre()) + ";"
                        + limpiarTexto(producto.getCategoria()) + ";"
                        + producto.getPrecio() + ";"
                        + producto.getCantidad() + ";"
                        + limpiarTexto(producto.getFechaVencimiento())
                );

                escritor.newLine();
            }
        }
    }
    
    

    // Cargar los productos guardados en el archivo
    public List<Producto> cargarProductos() throws IOException {

        List<Producto> productos = new ArrayList<>();

        if (!Files.exists(rutaArchivo)) {
            return productos;
        }

        try (BufferedReader lector = Files.newBufferedReader(
                rutaArchivo, StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";", -1);

                if (datos.length != 6) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String categoria = datos[2];
                    double precio = Double.parseDouble(datos[3]);
                    int cantidad = Integer.parseInt(datos[4]);
                    String fechaVencimiento = datos[5];

                    if (id <= 0
                        || nombre.isBlank()
                        || categoria.isBlank()
                        || !Double.isFinite(precio)
                        || precio < 0
                        || cantidad < 0
                        || fechaVencimiento.isBlank()) {
                    continue;
                }

                try {
                        LocalDate.parse(fechaVencimiento);
                    } catch (DateTimeParseException e) {
                        continue;
                }

                    productos.add(new Producto(
                            id,
                            nombre,
                            categoria,
                            precio,
                            cantidad,
                            fechaVencimiento
                    ));

                } catch (NumberFormatException e) {
                    // Ignorar registros con números inválidos
                }
            }
        }

        return productos;
    }

    // Evitar que los separadores dañen el formato del archivo
    private String limpiarTexto(String texto) {
        return texto.replace(";", ",")
                    .replace("\n", " ")
                    .replace("\r", " ");
    }
}