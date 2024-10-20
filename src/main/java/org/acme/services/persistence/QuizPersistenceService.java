package org.acme.services.persistence;

import org.acme.domain.models.Quiz;

public interface QuizPersistenceService {
    void createQuiz(Quiz quiz);
}
