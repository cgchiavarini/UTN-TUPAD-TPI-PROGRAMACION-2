/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entities;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Carlos Chiavarini
 */

public abstract class Base {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    protected Base(Long id) {
        validarId(id);
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor a cero.");
        }
    }

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void marcarComoEliminado() {
        this.eliminado = true;
    }

    public boolean estaActivo() {
        return !eliminado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Base base = (Base) obj;
        return Objects.equals(id, base.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
