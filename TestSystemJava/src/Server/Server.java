package Server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Server implements ServerInterface
{
    private String header;
    private Map<String, User> usersMap;
    private Console serverConsole;

    @Override
    public boolean userExists(String username) throws RemoteException
    {
        return usersMap.containsKey(username);
    }

    public Server()
    {
        super();
        usersMap = new HashMap<String, User>();
        serverConsole = new Console();
        header = "[SERVER]";
    }

    @Override
    public String register(String username, String password, String email) throws RemoteException
    {
        User newUser = new User(username, password, email);
        if(userExists(username))
        {
            return "User: " + username + " already exists." + "Please choose another nickname!.";
        }

        String message = "User: " + username + " has been registered!";
        serverConsole.printLog(header, message);
        return message;
    }

    @Override
    public User login(String username, String password) throws RemoteException
    {
        return new User("D","D","DD");
    }
}
