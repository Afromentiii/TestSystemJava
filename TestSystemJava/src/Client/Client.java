package Client;

import Service.InterfaceRMI;
import Service.User;

public class Client
{
    private User user;
    private InterfaceRMI serviceClient;

    public Client(User user, boolean isLoggedIn, InterfaceRMI serviceClient)
    {
        this.user = user;
        this.serviceClient = serviceClient;
    }

    public InterfaceRMI getServiceClient()
    {
        return serviceClient;
    }

    public void setServiceClient(InterfaceRMI serviceClient)
    {
        this.serviceClient = serviceClient;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
