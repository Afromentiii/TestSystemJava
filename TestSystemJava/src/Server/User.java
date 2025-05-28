package Server;

import java.io.Serializable;

public class User implements Serializable
{
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
