package org.acme.domain.exceptions;

public class QuestionAlreadyExistsException extends Exception {
    public QuestionAlreadyExistsException(String title) {
        super(String.format("Question \"%s\" is already present in the quiz.", title));
    }
}
