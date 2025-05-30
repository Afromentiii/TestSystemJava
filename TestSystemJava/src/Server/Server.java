package Server;

import Service.InterfaceRMI;
import Service.Question;
import Service.Test;
import Service.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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

    private boolean userExists(String username) throws RemoteException
    {
        return usersMap.containsKey(username);
    }

    public Server() throws RemoteException
    {
        super();
        usersMap = new HashMap<String, User>();
        serverConsole = new Console();
        header = "[SERVER]";
        questionsMainFilePath = "src/pytania.txt";
        testID = 0;
        testsMap =  new HashMap<Integer, Test>();
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
    public synchronized String register(String username, String password, String email, String firstName, String surname) throws RemoteException
    {
        if(userExists(username))
        {
            return "User: " + username + " already exists." + " Please choose another nickname!";
        }
        //Mozna dodac tu obsluge poprawnosci danych wedlug jakis kryteriow
        User newUser = new User(username, password, email, firstName, surname);
        String message = "User: " + username + " has been registered!";
        usersMap.put(username, newUser);
        serverConsole.printLog(header, message);
        return message;
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
        int howManyQuestions = 10;
        int countedLines = countLines();
        if (countedLines != notFound && howManyQuestions < countedLines)
        {
            List<Question> questions = Question.loadQuestions(questionsMainFilePath, howManyQuestions);
            Test test = new Test(questions, testID,6000);
            test.setUser(clientUser);
            testsMap.put(testID, test);
            for (Question question : test.getQuestions())
            {
                serverConsole.printLog(header, question.toString());
            }
            return new AbstractMap.SimpleImmutableEntry<>(testID, howManyQuestions);
        }
        return new AbstractMap.SimpleImmutableEntry<>(notFound, notFound);
    }

    @Override
    public synchronized int receiveTestScore(Test test) throws RemoteException
    {
        if(test != null)
        {
            int totalPoints = 0;
            for (Question question : test.getQuestions())
            {
                question.checkAnswer();
                totalPoints += question.getPoint();
            }
            return totalPoints;
        }
        return notFound;
    }
}
