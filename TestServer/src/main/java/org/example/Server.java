package org.example;

import javafx.util.Pair;
import org.example.InterfaceRMI;
import org.example.Question;
import org.example.Test;
import org.example.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;

public class Server extends UnicastRemoteObject implements InterfaceRMI
{
    private final String header;
    private final Map<String, User> usersMap;
    private final Console serverConsole;
    private final int notFound = -1;
    private final String questionsMainFilePath;
    private static int testID;
    private final Map<Integer, Test> testsMap;

    private boolean userExists(String username)
    {
        return usersMap.containsKey(username);
    }

    private boolean testExists(Integer testID)
    {
        return testsMap.containsKey(testID);
    }

    public Server() throws RemoteException
    {
        super();
        usersMap = new HashMap<String, User>();
        serverConsole = new Console();
        header = "[SERVER]";
        questionsMainFilePath = "pytania.txt";
        testID = 0;
        testsMap =  new HashMap<Integer, Test>();
    }

    private void saveStringToFile(String content, String filePath)
    {
        try (FileWriter writer = new FileWriter(filePath, true))
        {
            writer.write(content + System.lineSeparator());
            System.out.println("Zapisano do pliku: " + filePath);
        }
        catch (IOException e)
        {
            System.err.println("Wystąpił błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }

    private int countLines()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(questionsMainFilePath)))
        {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null)
            {
                lineNumber++;
            }
            return lineNumber;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return notFound;
    }

    @Override
    public synchronized Pair<Boolean, String> register(String username, String password, String email, String firstName, String surname) throws RemoteException
    {
        if(userExists(username))
        {
            return new Pair<Boolean,String>(Boolean.FALSE, "User: " + username + " already exists." + " Please choose another nickname!");
        }
        //Mozna dodac tu obsluge poprawnosci danych wedlug jakis kryteriow
        User newUser = new User(username, password, email, firstName, surname);
        String message = "User: " + username + " has been registered!";
        usersMap.put(username, newUser);
        serverConsole.printLog(header, message);
        return new Pair<Boolean, String>(Boolean.TRUE, message);
    }

    @Override
    public synchronized User login(String username, String password) throws RemoteException
    {
        if(userExists(username))
        {
            User foundUser = usersMap.get(username);
            if(foundUser.getPassword().equals(password))
            {
                if(!foundUser.getIsLoggedIn())
                {
                    foundUser.setLoggedIn(true);
                    serverConsole.printLog(header, "User: " + username + " has been logged in!");
                    return foundUser;
                }
            }
        }
        serverConsole.printLog(header, "User: " + username + " does not exist" + " or is logged in!");
        return null;
    }

    @Override
    public synchronized boolean logout(String username) throws RemoteException
    {
        if(userExists(username))
        {
            User foundUser = usersMap.get(username);
            foundUser.setLoggedIn(false);
            serverConsole.printLog(header, "User: " + foundUser.getName() + " has dissconnected!");
            return true;
        }
        serverConsole.printLog(header, "User: " + username + " does not exist");
        return false;
    }

    @Override
    public synchronized AbstractMap.SimpleImmutableEntry<Integer, Integer>  createTest(User clientUser) throws RemoteException
    {
        int howManyQuestions = 5;
        int countedLines = countLines();
        if (countedLines != notFound && howManyQuestions < countedLines)
        {
            List<Question> questions = Question.loadQuestions(questionsMainFilePath, howManyQuestions);
            Test test = new Test(questions, testID);
            test.setUser(clientUser);
            testsMap.put(testID, test);
            for (Question question : test.getQuestions())
            {
                serverConsole.printLog(header, question.toString());
            }
            Integer currentTestID = testID;
            User user = usersMap.get(clientUser.getName());
            if (user != null)
            {
                user.getTestsID().add(currentTestID);
            }
            testID++;
            return new AbstractMap.SimpleImmutableEntry<>(currentTestID, howManyQuestions);
        }
        return new AbstractMap.SimpleImmutableEntry<>(notFound, notFound);
    }

    @Override
    public synchronized Integer receiveTestScore(Integer testID) throws RemoteException
    {
        Test test = testsMap.get(testID);
        if(test != null)
        {
            int totalPoints = 0;
            for (Question question : test.getQuestions())
            {
                question.checkAnswer();
                totalPoints += question.getPoint();
            }
            test.setTestScore(totalPoints);
            LocalDateTime date = LocalDateTime.now();
            test.setDate(date);
            saveStringToFile(test.toString(), "testy.txt");
            return totalPoints;
        }
        return notFound;
    }

    @Override
    public synchronized Question getTestQuestion(Integer testID, Integer questionID) throws RemoteException
    {
        if(testExists(testID))
        {
            serverConsole.printLog(header, "Test: " + testID + " exists! " + "Sending question: " + questionID);
            return testsMap.get(testID).getQuestions().get(questionID);
        }
        return null;
    }

    @Override
    public synchronized boolean sendTestQuestion(Integer testID, Integer questionID, String answer) throws RemoteException
    {
        if(testExists(testID))
        {
            Test test = testsMap.get(testID);
            test.getQuestions().get(questionID).setUserAnswer(answer);
            serverConsole.printLog(header,"Test: " + testID + " got answer: " + answer + " Sending question: " + questionID);
            return true;
        }
        return false;
    }

    public synchronized List<Result> getUsersResults(String username) throws RemoteException
    {
        User foundUser = usersMap.get(username);
        if (foundUser != null)
        {
            List<Result> results = new ArrayList<>();
            for (Integer testID : foundUser.getTestsID())
            {
                Test test = testsMap.get(testID);
                int questionCount = test.getQuestions().size();
                int correctAnswerCount = test.getTestScore();
                double correctnessPercentage = ((double) correctAnswerCount / questionCount) * 100;
                LocalDateTime date = test.getDate();
                Result result = new Result(testID, date, questionCount, correctAnswerCount, correctnessPercentage);
                System.out.println(result.toString());
                results.add(result);
            }
            return results;
        }
        return  null;
    }
}