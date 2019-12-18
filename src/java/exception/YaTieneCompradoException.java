/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Usuario
 */
public class YaTieneCompradoException extends Exception {

    /**
     * Creates a new instance of <code>YaTieneCompradoException</code> without
     * detail message.
     */
    public YaTieneCompradoException() {
    }

    /**
     * Constructs an instance of <code>YaTieneCompradoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public YaTieneCompradoException(String msg) {
        super(msg);
    }
}
