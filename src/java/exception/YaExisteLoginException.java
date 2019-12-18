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
public class YaExisteLoginException extends Exception {

    /**
     * Creates a new instance of <code>YaExisteLoginException</code> without
     * detail message.
     */
    public YaExisteLoginException() {
    }

    /**
     * Constructs an instance of <code>YaExisteLoginException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public YaExisteLoginException(String msg) {
        super(msg);
    }
}
