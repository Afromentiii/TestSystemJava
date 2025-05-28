package Client;

import Server.Server;
import Service.ServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain
{
    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ServerInterface serviceClient = (ServerInterface) registry.lookup("Testy");
            System.out.println(serviceClient.register("TEST", "TEST","TEST","TEST","TEST"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
