package edu.northeastern.mygym.model.user;

public class User {
    private final Profile profile;

    public User(String userName, String name, String email) {
        this.profile = new Profile(userName, name, email);
    }

    public User(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getUserName() {
        return profile.getUserName();
    }

    public String getName() {
        return profile.getName();
    }

    public String getEmail() {
        return profile.getEmail();
    }

    public void setUserName(String userName) {
        this.profile.setUserName(userName);
    }

    public void setName(String name) {
        this.profile.setName(name);
    }

    public void setEmail(String email) {
        this.profile.setEmail(email);
    }
}

