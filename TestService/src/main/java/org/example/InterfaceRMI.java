package org.example;

import javafx.util.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public interface InterfaceRMI extends Remote
{
    User login(String username, String password) throws RemoteException;
    Pair<Boolean, String> register(String username, String password, String email, String firstName, String surname) throws RemoteException;
    boolean logout(String username) throws RemoteException;
    AbstractMap.SimpleImmutableEntry<Integer, Integer> createTest(User user) throws RemoteException;
    Integer receiveTestScore(Integer testID) throws RemoteException;
    Question getTestQuestion(Integer testID, Integer questionID) throws RemoteException;
    boolean sendTestQuestion(Integer testID, Integer questionID, String answer) throws RemoteException;
    List<Result> getUsersResults(User user) throws RemoteException;
}