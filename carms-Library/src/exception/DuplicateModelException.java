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
public class DuplicateModelException extends Exception{


    public DuplicateModelException() {
    }


    public DuplicateModelException(String msg) {
        super(msg);
    }
}
