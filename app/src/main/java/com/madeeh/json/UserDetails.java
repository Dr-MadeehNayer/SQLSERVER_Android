package com.madeeh.json;

public class UserDetails
{
    private String id;

    private String lastName;

    private String userName;

    private String firstName;

    private String password;

    public UserDetails(String id, String lastName, String userName, String firstName, String password) {
        this.id = id;
        this.lastName = lastName;
        this.userName = userName;
        this.firstName = firstName;
        this.password = password;
    }



    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "[id = "+id+", lastName = "+lastName+", userName = "+userName+", firstName = "+firstName+", password = "+password+"]";
    }
}
