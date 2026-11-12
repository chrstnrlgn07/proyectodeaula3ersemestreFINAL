package controlador;

import modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;

public class GestorInventario {

    private final List<Producto> inventario;

    public GestorInventario() {
        inventario = new ArrayList<>();
    }
    
    public List<Producto> obtenerProductosStockBajo() {
        List<Producto> productosStockBajo = new ArrayList<>();

        for (Producto producto : inventario) {
            if (producto.getCantidad() <= 5) {
                productosStockBajo.add(producto);
            }
        }

        return productosStockBajo;
    }
    
    
    public List<Producto> obtenerProductosProximosAVencer() {
        List<Producto> productosPorVencer = new ArrayList<>();

        LocalDate hoy = LocalDate.now();

        for (Producto producto : inventario) {

            LocalDate fechaVencimiento = LocalDate.parse(
                    producto.getFechaVencimiento()
            );

            long diasRestantes = ChronoUnit.DAYS.between(
                    hoy,
                    fechaVencimiento
            );

            if (diasRestantes <= 7) {
                productosPorVencer.add(producto);
            }
        }

        return productosPorVencer;
    }


   
    public boolean registrarProducto(Producto producto) {

        if (producto == null
                || buscarProducto(producto.getId()) != null
                || producto.getNombre() == null
                || producto.getNombre().isBlank()
                || producto.getCategoria() == null
                || producto.getCategoria().isBlank()
                || producto.getPrecio() < 0
                || !Double.isFinite(producto.getPrecio())
                || producto.getCantidad() < 0
                || producto.getFechaVencimiento() == null
                || producto.getFechaVencimiento().isBlank()) {

            return false;
        }

        try {
            LocalDate.parse(producto.getFechaVencimiento());

        } catch (DateTimeParseException e) {
            return false;
        }

        inventario.add(producto);
        return true;
    }

    
    public List<Producto> listarProductos() {
        return new ArrayList<>(inventario);
    }

    
    public void cargarProductos(List<Producto> productos) {
        inventario.clear();
        inventario.addAll(productos);
    }

    
    public Producto buscarProducto(int id) {
        for (Producto producto : inventario) {
            if (producto.getId() == id) {
                return producto;
            }
        }

        return null;
    }

    
    public boolean actualizarProducto(
        int id,
        String nombre,
        String categoria,
        double precio,
        int cantidad,
        String fechaVencimiento) {

        Producto producto = buscarProducto(id);

        if (producto == null || nombre == null || nombre.isBlank()
                || categoria == null || categoria.isBlank()
                || fechaVencimiento == null || fechaVencimiento.isBlank()
                || precio < 0 || !Double.isFinite(precio) || cantidad < 0) {
            return false;
        }

        try {
            LocalDate.parse(fechaVencimiento);
        } catch (DateTimeParseException e) {
            return false;
        }

        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setFechaVencimiento(fechaVencimiento);

        return true;
    }

    
    public boolean eliminarProducto(int id) {
        Producto producto = buscarProducto(id);

        if (producto == null) {
            return false;
        }

        inventario.remove(producto);
        return true;
    }

    
    public boolean venderProducto(int id, int cantidad) {
        Producto producto = buscarProducto(id);

        if (producto == null || cantidad <= 0
                || cantidad > producto.getCantidad()) {
            return false;
        }

        producto.setCantidad(producto.getCantidad() - cantidad);
        return true;
    }

    
    public boolean reponerProducto(int id, int cantidad) {
        Producto producto = buscarProducto(id);

        if (producto == null || cantidad <= 0
                || cantidad > Integer.MAX_VALUE - producto.getCantidad()) {
            return false;
        }

        producto.setCantidad(producto.getCantidad() + cantidad);
        return true;
    }

    
    public int obtenerSiguienteId() {
        int mayorId = 0;

        for (Producto producto : inventario) {
            if (producto.getId() > mayorId) {
                mayorId = producto.getId();
            }
        }

        return mayorId + 1;
    }
}