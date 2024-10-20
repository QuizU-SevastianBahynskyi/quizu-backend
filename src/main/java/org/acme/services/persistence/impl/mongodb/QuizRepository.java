package org.acme.services.persistence.impl.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.models.Quiz;
import org.acme.services.persistence.QuizPersistenceService;

@ApplicationScoped
public class QuizRepository implements PanacheMongoRepositoryBase<Quiz, Integer>, QuizPersistenceService {


    @Override
    public void createQuiz(Quiz quiz) {
        persist(quiz);
    }
}
