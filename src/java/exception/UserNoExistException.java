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
public class UserNoExistException extends Exception {

    /**
     * Creates a new instance of <code>UserNoExistException</code> without
     * detail message.
     */
    public UserNoExistException() {
    }

    /**
     * Constructs an instance of <code>UserNoExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserNoExistException(String msg) {
        super(msg);
    }
}
