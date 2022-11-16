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
public class InvalidMenuOptionException extends Exception {
    
    public InvalidMenuOptionException() {
    }

    public InvalidMenuOptionException(String msg) {
        super(msg);
    }
    
}
