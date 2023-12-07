package edu.northeastern.mygym.model.user;

public class Profile {
    private String userName;
    private String name;
    private String email;

    public Profile(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Username: " + userName + "\nName: " + name + "\nEmail: " + email;
    }
}
