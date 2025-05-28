package Service;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Test implements Serializable
{
    private int id;
    private List<Question> questions;
    private final long testMaxTime;
    private boolean testEnabled;
    private int testScore;

    public Test(List<Question> questions, int id, long testMaxTime)
    {
        this.questions = questions;
        this.id = id;
        this.testMaxTime = testMaxTime;
        this.testEnabled = true;
        this.testScore = 0;
    }

    //NARAZIE TESTOWA
    public void startTest()
    {
        if (testEnabled)
        {
            Scanner scanner = new Scanner(System.in);
            long startTime = System.currentTimeMillis();

            for (Question q : questions)
            {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= testMaxTime)
                {
                    System.out.println("Czas na test minął!");
                    testEnabled = false;
                    break;
                }

                System.out.println(q.getQuestion());
                System.out.println("A: " + q.getAnswerA());
                System.out.println("B: " + q.getAnswerB());
                System.out.println("C: " + q.getAnswerC());

                System.out.print("Wpisz odpowiedź (A/B/C): ");


                String userAnswer = scanner.nextLine().trim();
                if (userAnswer.equalsIgnoreCase("A"))
                {
                    q.setUserAnswer(q.getAnswerA());
                }

                if (userAnswer.equalsIgnoreCase("B"))
                {
                    q.setUserAnswer(q.getAnswerB());
                }
                if (userAnswer.equalsIgnoreCase("C"))
                {
                    q.setUserAnswer(q.getAnswerC());
                }
                q.checkAnswer();

                elapsed = System.currentTimeMillis() - startTime;
                long remaining = testMaxTime - elapsed;
                System.out.println("Pozostało czasu: " + (remaining / 1000) + " sekund");
            }
        }
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
}
