package org.krams.tutorial.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Виталий on 03.02.2016.
 */
@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "TEST_ID")
    private int testId;

    @Column(name = "NUMBER")
    private int number;

    @Column(name = "TEXT")
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getText() {
        return text;
    }

    public void setText(String description) {
        this.text = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
