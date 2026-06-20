/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entities;
import Exceptions.ValidacionException;

/**
 *
 * @author Carlos Chiavarini
 */

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        setProducto(producto);
        setCantidad(cantidad);
        calcularSubtotal();
    }

    private void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new ValidacionException("La cantidad debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
    }

    private void setSubtotal(Double subtotal) {
        if (subtotal == null || subtotal < 0) {
            throw new ValidacionException("El subtotal no puede ser negativo.");
        }
        this.subtotal = subtotal;
    }

    private void setProducto(Producto producto) {
        if (producto == null || producto.isEliminado() || !producto.isDisponible()) {
            throw new ValidacionException("El producto no existe, está eliminado o no está disponible.");
        }
        this.producto = producto;
    }

    private void calcularSubtotal() {
        setSubtotal(cantidad * producto.getPrecio());
    }

    public boolean correspondeAlProducto(Producto producto) {
        return this.producto.equals(producto);
    }

    public int getCantidad() {
        return cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    @Override
    public String toString() {
        return "Detalle ID: " + getId()
                + " | Producto: " + producto.getNombre()
                + " | Cantidad: " + cantidad
                + " | Subtotal: $" + String.format("%.2f", subtotal);
    }
}