package Client;

import Service.InterfaceRMI;
import Service.Test;
import Service.User;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.AbstractMap;

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

            //Server tworzy obiekt test, Klient podaje id testu
            //createTest() dorzucic argument typu User, zwraca id,
            //getQuestions() id testu, numer pytania;
            System.out.println(serviceClient.login("TestUser", "Test"));
            AbstractMap.SimpleImmutableEntry<Integer, Integer> testPair = serviceClient.createTest(client.getUser());


            if(serviceClient.logout(client.getUser().getName()))
            {
                client.setUser(null);
            }

            System.out.println("Client " + client.getUser());
            System.out.println(serviceClient.login("TestUser", "Test"));

            Thread.sleep(50000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
