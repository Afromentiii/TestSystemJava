package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

public interface InterfaceRMI extends Remote
{
    User login(String username, String password) throws RemoteException;
    String register(String username, String password, String email, String firstName, String surname) throws RemoteException;
    boolean logout(String username) throws RemoteException;
    AbstractMap.SimpleImmutableEntry<Integer, Integer> createTest(User user) throws RemoteException;
    int receiveTestScore(Test test) throws RemoteException;
    Question getTestQuestion(Integer testID, Integer questionID) throws RemoteException;
    boolean sendTestQuestion(Integer testID, Question question) throws RemoteException;
}
