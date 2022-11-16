/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author ongyongen
 */
public class OutletDoesNotExistException extends Exception {

    public OutletDoesNotExistException() {
    }

    public OutletDoesNotExistException(String msg) {
        super(msg);
    }

}
