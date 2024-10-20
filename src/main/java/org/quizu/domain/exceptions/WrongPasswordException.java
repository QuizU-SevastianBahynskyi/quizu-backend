package org.quizu.domain.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(){
        super("The password is incorrect.");
    }

    public WrongPasswordException(String message){
        super(message);
    }
}
