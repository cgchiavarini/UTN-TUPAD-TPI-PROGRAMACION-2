/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;
import Entities.Categoria;
import Entities.Producto;
import Exceptions.EntidadNoEncontradaException;
import Exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */

public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private Long ultimoId = 0L;

    public Producto crearProducto(
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            String imagen,
            boolean disponible,
            Categoria categoria) {

        validarNombreDisponible(nombre);

        Producto producto = new Producto(
                ++ultimoId,
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        productos.add(producto);

        return producto;
    }

    public List<Producto> listarProductos() {
        List<Producto> productosActivos = new ArrayList<>();

        for (Producto producto : productos) {
            if (producto.estaActivo()) {
                productosActivos.add(producto);
            }
        }

        return productosActivos;
    }

    public List<Producto> listarProductosPorCategoria(Categoria categoria) {
        List<Producto> productosDeCategoria = new ArrayList<>();

        for (Producto producto : productos) {
            if (producto.estaActivo() && producto.getCategoria().equals(categoria)) {
                productosDeCategoria.add(producto);
            }
        }

        return productosDeCategoria;
    }

    public Producto buscarPorId(Long id) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id) && producto.estaActivo()) {
                return producto;
            }
        }

        throw new EntidadNoEncontradaException("No existe un producto activo con id " + id + ".");
    }

    public void editarProducto(
            Long id,
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            String imagen,
            boolean disponible,
            Categoria categoria) {

        Producto producto = buscarPorId(id);

        if (!producto.getNombre().equalsIgnoreCase(nombre.trim())) {
            validarNombreDisponible(nombre);
        }

        producto.actualizarDatos(
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarPorId(id);
        producto.marcarComoEliminado();
    }

    private void validarNombreDisponible(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del producto no puede estar vacío.");
        }

        for (Producto producto : productos) {
            if (producto.estaActivo() && producto.getNombre().equalsIgnoreCase(nombre.trim())) {
                throw new ValidacionException("Ya existe un producto activo con ese nombre.");
            }
        }
    }
}