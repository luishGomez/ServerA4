/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author 2dam
 */
public class NoExistOfertaException extends Exception {

    /**
     * Creates a new instance of <code>NoExistOfertaException</code> without
     * detail message.
     */
    public NoExistOfertaException() {
    }

    /**
     * Constructs an instance of <code>NoExistOfertaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoExistOfertaException(String msg) {
        super(msg);
    }
}
