/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Exceptions;

/**
 *
 * @author Carlos Chiavarini
 */

public class MailDuplicadoException extends RuntimeException {
    public MailDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
