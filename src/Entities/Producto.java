/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entities;
import Exceptions.StockInsuficienteException;
import Exceptions.ValidacionException;


/**
 *
 * @author Carlos Chiavarini
 */

public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    public Producto(Long id, String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) {
        super(id);
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
        setCategoria(categoria);
        categoria.asociarProducto(this);
    }

    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del producto no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    private void setPrecio(Double precio) {
        if (precio == null || precio < 0) {
            throw new ValidacionException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    private void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ValidacionException("La descripción del producto no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    private void setStock(int stock) {
        if (stock < 0) {
            throw new ValidacionException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    private void setImagen(String imagen) {
        this.imagen = imagen == null ? "" : imagen.trim();
    }

    private void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    private void setCategoria(Categoria categoria) {
        if (categoria == null || categoria.isEliminado()) {
            throw new ValidacionException("La categoría no existe o está eliminada.");
        }
        this.categoria = categoria;
    }

    public void actualizarDatos(String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria nuevaCategoria) {
        Categoria categoriaAnterior = this.categoria;

        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
        setCategoria(nuevaCategoria);

        if (!categoriaAnterior.equals(nuevaCategoria)) {
            categoriaAnterior.desasociarProducto(this);
            nuevaCategoria.asociarProducto(this);
        }
    }

    public void descontarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new ValidacionException("La cantidad debe ser mayor a cero.");
        }

        if (cantidad > stock) {
            throw new StockInsuficienteException("No hay stock suficiente para el producto: " + nombre);
        }

        stock -= cantidad;
    }

    public void reponerStock(int cantidad) {
        if (cantidad <= 0) {
            throw new ValidacionException("La cantidad a reponer debe ser mayor a cero.");
        }

        stock += cantidad;
    }

    public boolean mismoNombre(String nombre) {
        return this.nombre.equalsIgnoreCase(nombre.trim());
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Producto: " + nombre
                + " | Precio: $" + String.format("%.2f", precio)
                + " | Stock: " + stock
                + " | Disponible: " + (disponible ? "Sí" : "No")
                + " | Categoría: " + categoria.getNombre();
    }
}