package org.example;

import javafx.util.Pair;
import org.example.InterfaceRMI;
import org.example.Question;
import org.example.User;

import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.Scanner;

public class Client {
    private User user;
    InterfaceRMI serviceClient;

    public Client(User user, InterfaceRMI serviceClient) {
        this.user = user;
        this.serviceClient = serviceClient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void solveTest(AbstractMap.SimpleImmutableEntry<Integer, Integer> testPair) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < testPair.getValue(); i++) {
            Question question = serviceClient.getTestQuestion(testPair.getKey(), i);
            serviceClient.sendTestQuestion(testPair.getKey(), i, question.displayAndInput(scanner));
        }
        scanner.close();
    }

    public boolean loginUser(String username, String password) {
        try {
            this.user = this.serviceClient.login(username, password);
            return user != null;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Pair<Boolean, String> registerUser(String username, String password, String email, String firstName, String lastName) {
        Pair<Boolean, String> result;
        try{
            result = this.serviceClient.register(username, password, email, firstName, lastName);
        } catch (RemoteException e){
            e.printStackTrace();
            result = new Pair<Boolean, String>(Boolean.FALSE, "Connection error occured. Check your connection and try again.");
        }
        return result;
    }
}