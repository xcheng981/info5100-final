package edu.northeastern.mygym.model.user;

public class Admin extends User {

    public Admin(String userName, String name, String email) {
        super(userName, name, email);
    }

    public Admin(Profile profile) {
        super(profile);
    }

}