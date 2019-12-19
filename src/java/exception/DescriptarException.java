package exception;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class DescriptarException extends Exception {

    /**
     * Creates a new instance of <code>DescriptarException</code> without detail
     * message.
     */
    public DescriptarException() {
    }

    /**
     * Constructs an instance of <code>DescriptarException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DescriptarException(String msg) {
        super(msg);
    }
}
