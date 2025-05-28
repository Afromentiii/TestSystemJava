package Service;

import java.io.Serializable;

public class User implements Serializable
{
    private String name;
    private String email;
    private String password;
    private String firstName;
    private String surname;

    public User(String name, String email, String password, String firstName, String surname)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.surname = surname;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getName()
    {
        return name;
    }
}
