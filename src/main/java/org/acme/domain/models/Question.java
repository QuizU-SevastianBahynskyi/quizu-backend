package org.acme.domain.models;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.acme.domain.exceptions.AnswerOptionAlreadyExistsException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MongoEntity(collection = "question")
public class Question extends PanacheMongoEntity implements Serializable {
    private String title;

    private Set<AnswerOption> answerOptions;

    public Question() {}

    public Question(String title) {
        this.title = title;
        this.answerOptions = new HashSet<>();
    }

    public Question(String title, List<AnswerOption> wrongAnswers) {
        this.title = title;
        this.answerOptions = new HashSet<>(wrongAnswers);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(Set<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public void addAnswerOption(AnswerOption answerOption) throws AnswerOptionAlreadyExistsException {
        if(answerOptions.stream().anyMatch(opt -> opt.getAnswer().equals(answerOption.getAnswer()))) {
            throw new AnswerOptionAlreadyExistsException(answerOption.getAnswer());
        }

        this.answerOptions.add(answerOption);
    }

    public void addAnswerOptions(List<AnswerOption> answerOptions) throws AnswerOptionAlreadyExistsException {
        for(AnswerOption answerOption : answerOptions) {
            addAnswerOption(answerOption);
        }
    }
}
