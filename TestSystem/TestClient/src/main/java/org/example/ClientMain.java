package org.example;

import org.example.InterfaceRMI;
import org.example.Question;
import org.example.Test;
import org.example.User;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.AbstractMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain
{
    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            InterfaceRMI serviceClient = (InterfaceRMI) registry.lookup("Testy");
            Client client = new Client(null, serviceClient);

            System.out.println(serviceClient.register("TestUser", "Test", "janek2001@gmail.com", "Janek", "Kowalski"));

            User user = serviceClient.login("TestUser", "Test");
            if(user != null)
            {
                client.setUser(user);
                System.out.println(serviceClient.login("TestUser", "Test"));
                AbstractMap.SimpleImmutableEntry<Integer, Integer> testPair = client.serviceClient.createTest(client.getUser());
                client.solveTest(testPair);
                System.out.println(serviceClient.receiveTestScore(testPair.getKey()));

            }

            if(serviceClient.logout(client.getUser().getName()))
            {
                client.setUser(null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}