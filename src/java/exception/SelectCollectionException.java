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
public class SelectCollectionException extends Exception {

    /**
     * Creates a new instance of <code>SelectCollectionException</code> without
     * detail message.
     */
    public SelectCollectionException() {
    }

    /**
     * Constructs an instance of <code>SelectCollectionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SelectCollectionException(String msg) {
        super(msg);
    }
}
