package org.quizu.domain.exceptions;

public class WrongCredentialsException extends Exception{
    public WrongCredentialsException(){
        super("Specified email or password is incorrect");
    }

    public WrongCredentialsException(String message){
        super(message);
    }
}
