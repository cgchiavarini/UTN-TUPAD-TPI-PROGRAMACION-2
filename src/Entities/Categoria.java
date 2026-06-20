/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entities;
import Exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */


public class Categoria extends Base {
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.productos = new ArrayList<>();
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre de la categoría no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    private void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ValidacionException("La descripción de la categoría no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    public void actualizarDatos(String nombre, String descripcion) {
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public void asociarProducto(Producto producto) {
        if (producto == null) {
            throw new ValidacionException("No se puede asociar un producto nulo.");
        }
        if (!productos.contains(producto)) {
            productos.add(producto);
        }
    }

    public void desasociarProducto(Producto producto) {
        productos.remove(producto);
    }

    public boolean tieneProductosActivos() {
        for (Producto producto : productos) {
            if (producto.estaActivo()) {
                return true;
            }
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Categoría: " + nombre + " | Descripción: " + descripcion;
    }
}
