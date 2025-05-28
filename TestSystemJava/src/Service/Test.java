package Service;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable
{
    private int id;
    private List<Question> questions;

    public Test(List<Question> questions, int id)
    {
        this.questions = questions;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public List<Question> getQuestions()
    {
        return questions;
    }
}
