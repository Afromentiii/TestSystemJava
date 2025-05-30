package Client;

import Service.InterfaceRMI;
import Service.Question;
import Service.User;

import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.Scanner;

public class Client
{
    private User user;
    InterfaceRMI serviceClient;

    public Client(User user, InterfaceRMI serviceClient)
    {
        this.user = user;
        this.serviceClient = serviceClient;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void solveTest(AbstractMap.SimpleImmutableEntry <Integer, Integer> testPair) throws RemoteException
    {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < testPair.getValue(); i++)
        {
            Question question = serviceClient.getTestQuestion(testPair.getKey(), i);
            serviceClient.sendTestQuestion(testPair.getKey(), i, question.displayAndInput(scanner));
        }
        scanner.close();
    }
}
