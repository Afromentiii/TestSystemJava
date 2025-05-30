package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question implements Serializable
{
    private String question;

    private String answerA;
    private String answerB;
    private String answerC;
    private String correctAnswer;
    private String userAnswer;
    private int point;

    public Question(String question, String answerA, String answerB, String answerC, String correctAnswer)
    {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.correctAnswer = correctAnswer;
        this.point = 0;
        this.userAnswer = null;
    }

    @Override
    public String toString()
    {
        return "Question{" +
                "question='" + question + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }

    public static List<Question>  loadQuestions(String filePath, int howManyQuestions)
    {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            int lineCounter = 0;
            while ((line = br.readLine()) != null && !line.isEmpty() && lineCounter < howManyQuestions)
            {
                String[] parts = line.split("\\|");
                if (parts.length == 5)
                {
                    Question q = new Question(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    questions.add(q);
                    lineCounter++;
                }
                else
                {
                    System.out.println("Niepoprawny format linii: " + line);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return questions;
    }

    public String displayAndInput(Scanner scanner)
    {
        System.out.println(this.getQuestion());
        System.out.println("A: " + this.getAnswerA());
        System.out.println("B: " + this.getAnswerB());
        System.out.println("C: " + this.getAnswerC());

        System.out.print("Wpisz odpowiedź (A/B/C): ");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("A"))
        {
            answer = getAnswerA();
        }

        if (answer.equalsIgnoreCase("B"))
        {
            answer = getAnswerB();

        }
        if (answer.equalsIgnoreCase("C"))
        {
            answer = getAnswerC();

        }

        return answer;
    }

    public void setUserAnswer(String userAnswer)
    {
        this.userAnswer = userAnswer;
    }

    public void checkAnswer()
    {
        if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer))
        {
            point = 1;
        }
        else
        {
            point = 0;
        }
    }

    public String getQuestion()
    {
        return question;
    }

    public String getAnswerA()
    {
        return answerA;
    }

    public String getAnswerB()
    {
        return answerB;
    }

    public String getAnswerC()
    {
        return answerC;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public String getUserAnswer()
    {
        return userAnswer;
    }

    public int getPoint()
    {
        return point;
    }
}