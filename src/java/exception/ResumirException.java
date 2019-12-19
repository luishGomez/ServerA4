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
public class ResumirException extends Exception {

    /**
     * Creates a new instance of <code>ResumirException</code> without detail
     * message.
     */
    public ResumirException() {
    }

    /**
     * Constructs an instance of <code>ResumirException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ResumirException(String msg) {
        super(msg);
    }
}
