package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote
{
    User login(String username, String password) throws RemoteException;
    String register(String username, String password, String email) throws RemoteException;
    boolean userExists(String username) throws RemoteException;

}
