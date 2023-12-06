package edu.northeastern.mygym.model.user;

public class Member extends User {

    public Member(String userName, String name, String email) {
        super(userName, name, email);
    }

    public Member(Profile profile) {
        super(profile);
    }
}