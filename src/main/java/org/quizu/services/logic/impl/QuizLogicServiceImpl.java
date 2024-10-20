package org.quizu.services.logic.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quizu.domain.models.Quiz;
import org.quizu.services.logic.QuizLogicService;
import org.quizu.services.persistence.QuizPersistenceService;

@ApplicationScoped
public class QuizLogicServiceImpl implements QuizLogicService {
    @Inject
    QuizPersistenceService persistenceService;
    @Override
    public void createQuiz(Quiz quiz) {
        persistenceService.createQuiz(quiz);
    }
}
