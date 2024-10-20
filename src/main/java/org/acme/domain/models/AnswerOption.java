package org.acme.domain.models;

import java.io.Serializable;

public class AnswerOption implements Serializable {
    private String answer;
    private boolean isCorrect;

    public AnswerOption() {}

    public AnswerOption(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }
}

