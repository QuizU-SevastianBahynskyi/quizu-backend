package org.quizu.domain.exceptions;

public class RequiredPropertiesMissingException extends Exception{
    public RequiredPropertiesMissingException(){
        super("Required properties are missing");
    }

    public RequiredPropertiesMissingException(String message){
        super(message);
    }
}
