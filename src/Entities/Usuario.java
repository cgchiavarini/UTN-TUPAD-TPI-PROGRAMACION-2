/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entities;
import Exceptions.ValidacionException;
import enums.Rol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Carlos Chiavarini
 */

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario(Long id, String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) {
        super(id);
        this.pedidos = new ArrayList<>();
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasena(contrasena);
        setRol(rol);
    }

    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new ValidacionException("El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
    }

    private void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) throw new ValidacionException("El apellido no puede estar vacío.");
        this.apellido = apellido.trim();
    }

    private void setMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) throw new ValidacionException("El mail no puede estar vacío.");
        if (!mail.contains("@") || !mail.contains(".")) throw new ValidacionException("El formato del mail no es válido.");
        this.mail = mail.trim().toLowerCase();
    }

    private void setCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) throw new ValidacionException("El celular no puede estar vacío.");
        this.celular = celular.trim();
    }

    private void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) throw new ValidacionException("La contraseña no puede estar vacía.");
        this.contrasena = contrasena;
    }

    private void setRol(Rol rol) {
        if (rol == null) throw new ValidacionException("El rol no puede ser nulo.");
        this.rol = rol;
    }

    public void actualizarDatos(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) {
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasena(contrasena);
        setRol(rol);
    }

    public void asociarPedido(Pedido pedido) {
        if (pedido == null) throw new ValidacionException("No se puede asociar un pedido nulo.");
        if (!pedidos.contains(pedido)) pedidos.add(pedido);
    }

    public boolean tieneMail(String mail) {
        return this.mail.equalsIgnoreCase(mail.trim());
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Usuario: " + nombre + " " + apellido
                + " | Mail: " + mail
                + " | Celular: " + celular
                + " | Rol: " + rol;
    }
}