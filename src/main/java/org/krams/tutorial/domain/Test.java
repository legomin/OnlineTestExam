package org.krams.tutorial.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Виталий on 03.02.2016.
 */
@Entity
@Table(name = "TEST")
public class Test implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "DURATION")
    private int duration; //in seconds

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String description) {
        this.text = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
