/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Exceptions.EntidadNoEncontradaException;
import Exceptions.ValidacionException;
import Interfaces.Calculable;
import enums.Estado;
import enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */
public class Pedido extends Base implements Calculable {

    private static Long ultimoIdDetalle = 0L;

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.detalles = new ArrayList<>();
        setUsuario(usuario);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        setFormaPago(formaPago);
    }

    public void asociarAlUsuario() {
        usuario.asociarPedido(this);
    }

    private void setUsuario(Usuario usuario) {
        if (usuario == null || usuario.isEliminado()) {
            throw new ValidacionException("No se puede crear un pedido sin usuario activo.");
        }
        this.usuario = usuario;
    }

    private void setEstado(Estado estado) {
        if (estado == null) {
            throw new ValidacionException("El estado no puede ser nulo.");
        }
        this.estado = estado;
    }

    private void setFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new ValidacionException("La forma de pago no puede ser nula.");
        }
        this.formaPago = formaPago;
    }

    private void setTotal(Double total) {
        if (total == null || total < 0) {
            throw new ValidacionException("El total no puede ser negativo.");
        }
        this.total = total;
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        producto.descontarStock(cantidad);
        DetallePedido detalle = new DetallePedido(++ultimoIdDetalle, cantidad, producto);
        detalles.add(detalle);
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.correspondeAlProducto(producto) && detalle.estaActivo()) {
                return detalle;
            }
        }
        throw new EntidadNoEncontradaException("No existe un detalle para ese producto.");
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);
        detalle.marcarComoEliminado();
        detalle.getProducto().reponerStock(detalle.getCantidad());
        calcularTotal();
    }

    public boolean tieneDetallesActivos() {
        for (DetallePedido detalle : detalles) {
            if (detalle.estaActivo()) {
                return true;
            }
        }
        return false;
    }

    public void actualizarEstadoYFormaPago(Estado estado, FormaPago formaPago) {
        setEstado(estado);
        setFormaPago(formaPago);
    }

    @Override
    public void calcularTotal() {
        double suma = 0.0;

        for (DetallePedido detalle : detalles) {
            if (detalle.estaActivo()) {
                suma += detalle.getSubtotal();
            }
        }

        setTotal(suma);
    }

    @Override
    public void marcarComoEliminado() {
        super.marcarComoEliminado();

        for (DetallePedido detalle : detalles) {
            detalle.marcarComoEliminado();
        }
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<DetallePedido> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Usuario: " + usuario.getNombre() + " " + usuario.getApellido()
                + " | Estado: " + estado
                + " | Pago: " + formaPago
                + " | Total: $" + String.format("%.2f", total)
                + " | Fecha: " + fecha;
    }
}
