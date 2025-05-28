package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRMI extends Remote
{
    User login(String username, String password) throws RemoteException;
    String register(String username, String password, String email, String firstName, String surname) throws RemoteException;
    boolean logout(User loggedUser) throws RemoteException;
    Test createTest(int howManyQuestions) throws RemoteException;
}
