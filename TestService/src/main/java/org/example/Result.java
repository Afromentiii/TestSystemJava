package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Result implements Serializable {
    private int testId;
    private LocalDateTime sentAt;
    private int questionCount;
    private int correctAnswerCount;
    private double correctnessPercentage;


    public Result(int testId, LocalDateTime sentAt, int questionCount, int correctAnswerCount, double correctnessPercentage) {
        this.testId = testId;
        this.sentAt = sentAt;
        this.questionCount = questionCount;
        this.correctAnswerCount = correctAnswerCount;
        this.correctnessPercentage = correctnessPercentage;
    }

    public int getTestId() {
        return testId;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public double getCorrectnessPercentage() {
        return correctnessPercentage;
    }
}
