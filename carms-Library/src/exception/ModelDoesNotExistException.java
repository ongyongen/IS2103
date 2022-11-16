/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author jiaen
 */
public class ModelDoesNotExistException extends Exception{


    public ModelDoesNotExistException() {
    }

    public ModelDoesNotExistException(String msg) {
        super(msg);
    }
}
