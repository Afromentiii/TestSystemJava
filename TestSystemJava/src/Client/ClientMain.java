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
            Client client = new Client(null, serviceClient);

            System.out.println(client.getServiceClient().register("TestUser", "Test", "janek2001@gmail.com", "Janek", "Kowalski"));
            System.out.println(client.getServiceClient().login("TestUser", "Test"));
            System.out.println(client.getServiceClient().login("TestUser", "Test"));

            Thread.sleep(5000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
