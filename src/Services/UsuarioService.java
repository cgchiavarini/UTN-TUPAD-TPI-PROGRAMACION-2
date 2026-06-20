/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;
import Entities.Usuario;
import Exceptions.EntidadNoEncontradaException;
import Exceptions.MailDuplicadoException;
import Exceptions.ValidacionException;
import enums.Rol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private Long ultimoId = 0L;

    public Usuario crearUsuario(
            String nombre,
            String apellido,
            String mail,
            String celular,
            String contrasena,
            Rol rol) {

        validarMailDisponible(mail);

        Usuario usuario = new Usuario(
                ++ultimoId,
                nombre,
                apellido,
                mail,
                celular,
                contrasena,
                rol
        );

        usuarios.add(usuario);

        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuariosActivos = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (usuario.estaActivo()) {
                usuariosActivos.add(usuario);
            }
        }

        return usuariosActivos;
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id) && usuario.estaActivo()) {
                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("No existe un usuario activo con id " + id + ".");
    }

    public void editarUsuario(
            Long id,
            String nombre,
            String apellido,
            String mail,
            String celular,
            String contrasena,
            Rol rol) {

        Usuario usuario = buscarPorId(id);

        if (!usuario.getMail().equalsIgnoreCase(mail.trim())) {
            validarMailDisponible(mail);
        }

        usuario.actualizarDatos(
                nombre,
                apellido,
                mail,
                celular,
                contrasena,
                rol
        );
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.marcarComoEliminado();
    }

    private void validarMailDisponible(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new ValidacionException("El mail no puede estar vacío.");
        }

        for (Usuario usuario : usuarios) {
            if (usuario.estaActivo() && usuario.getMail().equalsIgnoreCase(mail.trim())) {
                throw new MailDuplicadoException("Ya existe un usuario activo con ese mail.");
            }
        }
    }
}