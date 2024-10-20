package org.acme.services.logic.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.models.Quiz;
import org.acme.services.logic.QuizLogicService;
import org.acme.services.persistence.QuizPersistenceService;

@ApplicationScoped
public class QuizLogicServiceImpl implements QuizLogicService {
    @Inject
    QuizPersistenceService persistenceService;
    @Override
    public void createQuiz(Quiz quiz) {
        persistenceService.createQuiz(quiz);
    }
}
