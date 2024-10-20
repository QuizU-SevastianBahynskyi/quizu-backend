package org.acme.domain.exceptions;

public class AnswerOptionAlreadyExistsException extends Exception {
    public AnswerOptionAlreadyExistsException(String answer) {
        super(String.format("Answer option \"%s\" already exists in the question.", answer));
    }
}
