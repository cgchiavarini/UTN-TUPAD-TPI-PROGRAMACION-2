/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;
import Entities.Categoria;
import Exceptions.EntidadNoEncontradaException;
import Exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */

public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private Long ultimoId = 0L;

    public Categoria crearCategoria(String nombre, String descripcion) {
        validarNombreDisponible(nombre);

        Categoria categoria = new Categoria(++ultimoId, nombre, descripcion);
        categorias.add(categoria);

        return categoria;
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> categoriasActivas = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (categoria.estaActivo()) {
                categoriasActivas.add(categoria);
            }
        }

        return categoriasActivas;
    }

    public Categoria buscarPorId(Long id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id) && categoria.estaActivo()) {
                return categoria;
            }
        }

        throw new EntidadNoEncontradaException("No existe una categoría activa con id " + id + ".");
    }

    public void editarCategoria(Long id, String nombre, String descripcion) {
        Categoria categoria = buscarPorId(id);

        if (!categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
            validarNombreDisponible(nombre);
        }

        categoria.actualizarDatos(nombre, descripcion);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = buscarPorId(id);

        if (categoria.tieneProductosActivos()) {
            throw new ValidacionException("No se puede eliminar la categoría porque tiene productos activos asociados.");
        }

        categoria.marcarComoEliminado();
    }

    private void validarNombreDisponible(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre de la categoría no puede estar vacío.");
        }

        for (Categoria categoria : categorias) {
            if (categoria.estaActivo() && categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
                throw new ValidacionException("Ya existe una categoría activa con ese nombre.");
            }
        }
    }
}