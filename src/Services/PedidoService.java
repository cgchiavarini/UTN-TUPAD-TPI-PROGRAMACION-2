/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;
import Entities.Pedido;
import Entities.Producto;
import Entities.Usuario;
import Exceptions.EntidadNoEncontradaException;
import Exceptions.ValidacionException;
import enums.Estado;
import enums.FormaPago;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */

public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();
    private Long ultimoId = 0L;

    public Pedido crearPedido(
            Usuario usuario,
            FormaPago formaPago,
            List<Producto> productos,
            List<Integer> cantidades) {

        validarDatosPedido(usuario, formaPago, productos, cantidades);

        Pedido pedido = new Pedido(++ultimoId, usuario, formaPago);

        try {
            for (int i = 0; i < productos.size(); i++) {
                pedido.addDetallePedido(cantidades.get(i), productos.get(i));
            }

            if (!pedido.tieneDetallesActivos()) {
                throw new ValidacionException("El pedido debe tener al menos un detalle.");
            }

            pedido.calcularTotal();
            pedido.asociarAlUsuario();
            pedidos.add(pedido);

            return pedido;

        } catch (RuntimeException e) {
            pedido.marcarComoEliminado();
            throw new ValidacionException("No se pudo crear el pedido. Operación cancelada: " + e.getMessage());
        }
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidosActivos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.estaActivo()) {
                pedidosActivos.add(pedido);
            }
        }

        return pedidosActivos;
    }

    public List<Pedido> listarPedidosPorUsuario(Usuario usuario) {
        List<Pedido> pedidosDelUsuario = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.estaActivo() && pedido.getUsuario().equals(usuario)) {
                pedidosDelUsuario.add(pedido);
            }
        }

        return pedidosDelUsuario;
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId().equals(id) && pedido.estaActivo()) {
                return pedido;
            }
        }

        throw new EntidadNoEncontradaException("No existe un pedido activo con id " + id + ".");
    }

    public void actualizarEstadoYFormaPago(Long id, Estado estado, FormaPago formaPago) {
        Pedido pedido = buscarPorId(id);
        pedido.actualizarEstadoYFormaPago(estado, formaPago);
    }

    public void eliminarPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.marcarComoEliminado();
    }

    private void validarDatosPedido(
            Usuario usuario,
            FormaPago formaPago,
            List<Producto> productos,
            List<Integer> cantidades) {

        if (usuario == null || usuario.isEliminado()) {
            throw new ValidacionException("El pedido debe tener un usuario activo.");
        }

        if (formaPago == null) {
            throw new ValidacionException("El pedido debe tener una forma de pago.");
        }

        if (productos == null || productos.isEmpty()) {
            throw new ValidacionException("El pedido debe tener al menos un producto.");
        }

        if (cantidades == null || cantidades.isEmpty()) {
            throw new ValidacionException("El pedido debe tener al menos una cantidad.");
        }

        if (productos.size() != cantidades.size()) {
            throw new ValidacionException("La cantidad de productos y cantidades no coincide.");
        }
    }
}