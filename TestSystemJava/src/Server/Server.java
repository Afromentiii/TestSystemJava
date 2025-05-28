package Server;

import java.rmi.RemoteException;

public class Server implements ServerInterface
{

    public Server()
    {
        super();
    }
    @Override
    public String register(String username, String password, String email) throws RemoteException {
        return "";
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        return "";
    }
}
