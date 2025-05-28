package Client;

import Service.InterfaceRMI;
import Service.User;

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

            System.out.println(serviceClient.register("TestUser", "Test", "janek2001@gmail.com", "Janek", "Kowalski"));

            User user = serviceClient.login("TestUser", "Test");
            if(user != null)
            {
                client.setUser(user);
            }

            System.out.println(serviceClient.login("TestUser", "Test"));
            serviceClient.createTest(10);

            if(serviceClient.logout(client.getUser()))
            {
                client.setUser(null);
            }

            System.out.println("Client " + client.getUser());

            Thread.sleep(5000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
