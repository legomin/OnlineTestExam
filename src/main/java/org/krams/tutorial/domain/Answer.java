package org.krams.tutorial.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Виталий on 03.02.2016.
 */
@Entity
@Table(name = "ANSWER")
public class Answer implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "QUESTION_ID")
    private int questionId;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "CORRECT")
    private boolean correct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
