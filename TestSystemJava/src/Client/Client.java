package Client;

import Service.InterfaceRMI;
import Service.User;

public class Client
{
    private User user;

    public Client(User user, InterfaceRMI serviceClient)
    {
        this.user = user;
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
