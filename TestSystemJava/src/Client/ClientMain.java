package Client;

import Service.InterfaceRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain
{
    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            InterfaceRMI serviceClient = (InterfaceRMI) registry.lookup("Testy");
            Client client = new Client(null, false, serviceClient);



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
