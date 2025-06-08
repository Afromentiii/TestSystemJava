package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String password;
    private String firstName;
    private String surname;
    private boolean isLoggedIn;
    private List<Integer> testsID;

    public User(String name, String password, String email, String firstName, String surname)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.surname = surname;
        this.testsID = new ArrayList<>();
        this.isLoggedIn = false;
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

    public boolean getIsLoggedIn()
    {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        isLoggedIn = loggedIn;
    }

    public List<Integer> getTestsID()
    {
        return testsID;
    }
}