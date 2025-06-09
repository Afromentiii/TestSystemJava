package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Test implements Serializable
{
    private User user;
    private int id;
    private List<Question> questions;
    private boolean testEnabled;
    private int testScore;
    private LocalDateTime date;

    public Test(List<Question> questions, int id)
    {
        this.questions = questions;
        this.id = id;
        this.testEnabled = true;
        this.testScore = 0;
    }

    public int getId()
    {
        return id;
    }

    public List<Question> getQuestions()
    {
        return questions;
    }

    public int getTestScore()
    {
        return testScore;
    }

    public void setTestScore(int testScore)
    {
        this.testScore = testScore;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "Test{" +
                "id=" + id +
                ", username=" + user.getName() +
                ", questions=" + questions +
                ", testEnabled=" + testEnabled +
                ", testScore=" + testScore +
                '}';
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}