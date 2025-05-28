package Server;

import Service.InterfaceRMI;
import Service.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server extends UnicastRemoteObject implements InterfaceRMI
{
    private final String header;
    private final Map<String, User> usersMap;
    private final Console serverConsole;

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
    }

    @Override
    public String register(String username, String password, String email, String firstName, String surname) throws RemoteException
    {
        if(userExists(username))
        {
            return "User: " + username + " already exists." + " Please choose another nickname!";
        }

        User newUser = new User(username, password, email, firstName, surname);
        String message = "User: " + username + " has been registered!";
        usersMap.put(username, newUser);
        serverConsole.printLog(header, message);
        return message;
    }

    @Override
    public User login(String username, String password) throws RemoteException
    {
       if(userExists(username))
       {
           if(usersMap.get(username).getPassword().equals(password))
           {
               return usersMap.get(username);
           }
       }
       return null;
    }
}
