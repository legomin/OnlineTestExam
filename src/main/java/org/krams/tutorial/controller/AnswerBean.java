package org.krams.tutorial.controller;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виталий on 05.02.2016.
 */
public class AnswerBean {
    @NotEmpty
    private List<String> answer = new ArrayList();
    private String id;

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
