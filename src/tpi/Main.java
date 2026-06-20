/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpi;
import Entities.Categoria;
import Entities.Pedido;
import Entities.Producto;
import Entities.Usuario;
import Exceptions.ValidacionException;
import Services.CategoriaService;
import Services.PedidoService;
import Services.ProductoService;
import Services.UsuarioService;
import Utils.InputHelper;
import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Carlos Chiavarini
 */

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InputHelper input = new InputHelper(scanner);

    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        cargarDatosIniciales();
        mostrarMenuPrincipal();
    }

    private static void mostrarMenuPrincipal() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== SISTEMA DE PEDIDOS FOOD STORE ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");

            opcion = input.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void menuCategorias() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== GESTIÓN DE CATEGORÍAS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = input.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarCategorias();
                    case 2 -> crearCategoria();
                    case 3 -> editarCategoria();
                    case 4 -> eliminarCategoria();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (opcion != 0) {
                input.pausar();
            }

        } while (opcion != 0);
    }

    private static void listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private static void crearCategoria() {
        String nombre = input.leerTexto("Nombre: ");
        String descripcion = input.leerTexto("Descripción: ");

        Categoria categoria = categoriaService.crearCategoria(nombre, descripcion);

        System.out.println("Categoría creada correctamente. ID generado: " + categoria.getId());
    }

    private static void editarCategoria() {
        listarCategorias();

        Long id = input.leerLong("Ingrese el ID de la categoría a editar: ");
        Categoria categoriaActual = categoriaService.buscarPorId(id);

        String nombre = input.leerTextoOpcional("Nuevo nombre", categoriaActual.getNombre());
        String descripcion = input.leerTextoOpcional("Nueva descripción", categoriaActual.getDescripcion());

        categoriaService.editarCategoria(id, nombre, descripcion);

        System.out.println("Categoría editada correctamente.");
    }

    private static void eliminarCategoria() {
        listarCategorias();

        Long id = input.leerLong("Ingrese el ID de la categoría a eliminar: ");
        boolean confirma = input.leerBoolean("¿Confirma la eliminación?");

        if (confirma) {
            categoriaService.eliminarCategoria(id);
            System.out.println("Categoría eliminada correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void menuProductos() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== GESTIÓN DE PRODUCTOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = input.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarProductos();
                    case 2 -> crearProducto();
                    case 3 -> editarProducto();
                    case 4 -> eliminarProducto();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (opcion != 0) {
                input.pausar();
            }

        } while (opcion != 0);
    }

    private static void listarProductos() {
        List<Producto> productos = productoService.listarProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private static void crearProducto() {
        listarCategorias();

        Long idCategoria = input.leerLong("ID de categoría: ");
        Categoria categoria = categoriaService.buscarPorId(idCategoria);

        String nombre = input.leerTexto("Nombre: ");
        String descripcion = input.leerTexto("Descripción: ");
        Double precio = input.leerDouble("Precio: ");
        int stock = input.leerEntero("Stock: ");
        String imagen = input.leerTexto("Imagen: ");
        boolean disponible = input.leerBoolean("¿Disponible?");

        Producto producto = productoService.crearProducto(
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        System.out.println("Producto creado correctamente. ID generado: " + producto.getId());
    }

    private static void editarProducto() {
        listarProductos();

        Long id = input.leerLong("Ingrese el ID del producto a editar: ");
        Producto productoActual = productoService.buscarPorId(id);

        listarCategorias();

        Long idCategoria = input.leerLong("Nueva categoría ID: ");
        Categoria categoria = categoriaService.buscarPorId(idCategoria);

        String nombre = input.leerTextoOpcional("Nuevo nombre", productoActual.getNombre());
        String descripcion = input.leerTextoOpcional("Nueva descripción", productoActual.getDescripcion());
        Double precio = input.leerDoubleOpcional("Nuevo precio", productoActual.getPrecio());
        int stock = input.leerEnteroOpcional("Nuevo stock", productoActual.getStock());
        String imagen = input.leerTextoOpcional("Nueva imagen", productoActual.getImagen());
        boolean disponible = input.leerBooleanOpcional("¿Disponible?", productoActual.isDisponible());

        productoService.editarProducto(
                id,
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        System.out.println("Producto editado correctamente.");
    }

    private static void eliminarProducto() {
        listarProductos();

        Long id = input.leerLong("Ingrese el ID del producto a eliminar: ");
        boolean confirma = input.leerBoolean("¿Confirma la eliminación?");

        if (confirma) {
            productoService.eliminarProducto(id);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private static void menuUsuarios() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== GESTIÓN DE USUARIOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = input.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarUsuarios();
                    case 2 -> crearUsuario();
                    case 3 -> editarUsuario();
                    case 4 -> eliminarUsuario();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (opcion != 0) {
                input.pausar();
            }

        } while (opcion != 0);
    }

    private static void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private static void crearUsuario() {
        String nombre = input.leerTexto("Nombre: ");
        String apellido = input.leerTexto("Apellido: ");
        String mail = input.leerTexto("Mail: ");
        String celular = input.leerTexto("Celular: ");
        String contrasena = input.leerTexto("Contraseña: ");
        Rol rol = seleccionarRol();

        Usuario usuario = usuarioService.crearUsuario(
                nombre,
                apellido,
                mail,
                celular,
                contrasena,
                rol
        );

        System.out.println("Usuario creado correctamente. ID generado: " + usuario.getId());
    }

    private static void editarUsuario() {
        listarUsuarios();

        Long id = input.leerLong("Ingrese el ID del usuario a editar: ");
        Usuario usuarioActual = usuarioService.buscarPorId(id);

        String nombre = input.leerTextoOpcional("Nuevo nombre", usuarioActual.getNombre());
        String apellido = input.leerTextoOpcional("Nuevo apellido", usuarioActual.getApellido());
        String mail = input.leerTextoOpcional("Nuevo mail", usuarioActual.getMail());
        String celular = input.leerTextoOpcional("Nuevo celular", usuarioActual.getCelular());
        String contrasena = input.leerTextoOpcional("Nueva contraseña", usuarioActual.getContrasena());
        Rol rol = seleccionarRol();

        usuarioService.editarUsuario(
                id,
                nombre,
                apellido,
                mail,
                celular,
                contrasena,
                rol
        );

        System.out.println("Usuario editado correctamente.");
    }

    private static void eliminarUsuario() {
        listarUsuarios();

        Long id = input.leerLong("Ingrese el ID del usuario a eliminar: ");
        boolean confirma = input.leerBoolean("¿Confirma la eliminación?");

        if (confirma) {
            usuarioService.eliminarUsuario(id);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void menuPedidos() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== GESTIÓN DE PEDIDOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = input.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarPedidos();
                    case 2 -> crearPedido();
                    case 3 -> actualizarPedido();
                    case 4 -> eliminarPedido();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (opcion != 0) {
                input.pausar();
            }

        } while (opcion != 0);
    }

    private static void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);

            for (var detalle : pedido.getDetalles()) {
                if (detalle.estaActivo()) {
                    System.out.println("   - " + detalle);
                }
            }
        }
    }

    private static void crearPedido() {
        listarUsuarios();

        Long idUsuario = input.leerLong("ID del usuario: ");
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        FormaPago formaPago = seleccionarFormaPago();

        List<Producto> productos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        boolean agregarOtro;

        do {
            listarProductos();

            Long idProducto = input.leerLong("ID del producto: ");
            Producto producto = productoService.buscarPorId(idProducto);

            int cantidad = input.leerEntero("Cantidad: ");

            productos.add(producto);
            cantidades.add(cantidad);

            agregarOtro = input.leerBoolean("¿Desea agregar otro producto?");

        } while (agregarOtro);

        Pedido pedido = pedidoService.crearPedido(
                usuario,
                formaPago,
                productos,
                cantidades
        );

        System.out.println("Pedido creado correctamente. ID generado: " + pedido.getId());
        System.out.println("Total del pedido: $" + String.format("%.2f", pedido.getTotal()));
    }

    private static void actualizarPedido() {
        listarPedidos();

        Long id = input.leerLong("Ingrese el ID del pedido a actualizar: ");

        Estado estado = seleccionarEstado();
        FormaPago formaPago = seleccionarFormaPago();

        pedidoService.actualizarEstadoYFormaPago(id, estado, formaPago);

        System.out.println("Pedido actualizado correctamente.");
    }

    private static void eliminarPedido() {
        listarPedidos();

        Long id = input.leerLong("Ingrese el ID del pedido a eliminar: ");
        boolean confirma = input.leerBoolean("¿Confirma la eliminación?");

        if (confirma) {
            pedidoService.eliminarPedido(id);
            System.out.println("Pedido eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static Rol seleccionarRol() {
        while (true) {
            System.out.println();
            System.out.println("Seleccione rol:");
            System.out.println("1. ADMIN");
            System.out.println("2. USUARIO");

            int opcion = input.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> {
                    return Rol.ADMIN;
                }
                case 2 -> {
                    return Rol.USUARIO;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static Estado seleccionarEstado() {
        while (true) {
            System.out.println();
            System.out.println("Seleccione estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. CONFIRMADO");
            System.out.println("3. TERMINADO");
            System.out.println("4. CANCELADO");

            int opcion = input.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> {
                    return Estado.PENDIENTE;
                }
                case 2 -> {
                    return Estado.CONFIRMADO;
                }
                case 3 -> {
                    return Estado.TERMINADO;
                }
                case 4 -> {
                    return Estado.CANCELADO;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static FormaPago seleccionarFormaPago() {
        while (true) {
            System.out.println();
            System.out.println("Seleccione forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");

            int opcion = input.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> {
                    return FormaPago.TARJETA;
                }
                case 2 -> {
                    return FormaPago.TRANSFERENCIA;
                }
                case 3 -> {
                    return FormaPago.EFECTIVO;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void cargarDatosIniciales() {
        try {
            Categoria hamburguesas = categoriaService.crearCategoria(
                    "Hamburguesas",
                    "Hamburguesas simples, dobles y especiales"
            );

            Categoria bebidas = categoriaService.crearCategoria(
                    "Bebidas",
                    "Gaseosas, aguas y jugos"
            );

            productoService.crearProducto(
                    "Hamburguesa Simple",
                    4500.0,
                    "Hamburguesa con carne, queso y pan artesanal",
                    20,
                    "hamburguesa-simple.jpg",
                    true,
                    hamburguesas
            );

            productoService.crearProducto(
                    "Hamburguesa Doble",
                    6500.0,
                    "Hamburguesa con doble carne y doble queso",
                    15,
                    "hamburguesa-doble.jpg",
                    true,
                    hamburguesas
            );

            productoService.crearProducto(
                    "Coca Cola",
                    1800.0,
                    "Gaseosa línea Coca Cola 500ml",
                    30,
                    "coca-cola.jpg",
                    true,
                    bebidas
            );

            usuarioService.crearUsuario(
                    "Carlos",
                    "Chiavarini",
                    "carlos@mail.com",
                    "3420000000",
                    "1234",
                    Rol.ADMIN
            );

            usuarioService.crearUsuario(
                    "Usuario",
                    "Prueba",
                    "usuario@mail.com",
                    "3421111111",
                    "1234",
                    Rol.USUARIO
            );

        } catch (RuntimeException e) {
            throw new ValidacionException("Error al cargar datos iniciales: " + e.getMessage());
        }
    }
}