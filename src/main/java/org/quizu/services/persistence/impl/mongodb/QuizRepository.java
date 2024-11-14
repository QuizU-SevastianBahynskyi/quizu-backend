package org.quizu.services.persistence.impl.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.quizu.domain.models.Quiz;
import org.quizu.services.persistence.QuizPersistenceService;

@ApplicationScoped
public class QuizRepository implements PanacheMongoRepositoryBase<Quiz, Integer>, QuizPersistenceService {


    @Override
    public void createQuiz(Quiz quiz) {
        persist(quiz);
    }
}
