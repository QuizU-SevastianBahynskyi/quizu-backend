package org.acme.domain.models;

import com.google.gson.Gson;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.acme.domain.exceptions.QuestionAlreadyExistsException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MongoEntity(collection = "quiz")
public class Quiz {
    private String quizOwnerEmail;
    private String title;
    private String description;

    private Set<Question> questions;

    public Quiz() {
    }

    public Quiz(String quizOwnerEmail, String title, String description, List<Question> questions) {
        this.quizOwnerEmail = quizOwnerEmail;
        this.title = title;
        this.description = description;
        this.questions = new HashSet<>(questions);
    }

    public Quiz(String quizOwnerEmail, String title, String description) {
        this.quizOwnerEmail = quizOwnerEmail;
        this.title = title;
        this.description = description;
        this.questions = new HashSet<>();
    }

    public Quiz(String quizOwnerEmail, String title) {
        this.quizOwnerEmail = quizOwnerEmail;
        this.title = title;
        this.questions = new HashSet<>();
    }

    public String getQuizOwnerEmail() {
        return quizOwnerEmail;
    }

    public void setQuizOwnerEmail(String quizOwnerEmail) {
        this.quizOwnerEmail = quizOwnerEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addQuestion(Question question) throws QuestionAlreadyExistsException {
        if (questions.stream().anyMatch(q -> q.getTitle().equals(question.getTitle()))) {
            throw new QuestionAlreadyExistsException(question.getTitle());
        }
        this.questions.add(question);
    }
    // TODO: investigate whether I need the add question method here, or directly in the repository

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
