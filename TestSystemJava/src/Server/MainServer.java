package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer
{
    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.createRegistry(1099);
            Server serverInstance = new Server();
            registry.rebind("Testy", serverInstance);
            System.out.println("Server started!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
