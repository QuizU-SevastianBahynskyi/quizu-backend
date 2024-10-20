package org.quizu.services.persistence;

import org.quizu.domain.models.Quiz;

public interface QuizPersistenceService {
    void createQuiz(Quiz quiz);
}
