package edu.northeastern.mygym;

public class UserInformation {
    private String username;
    private String name;
    private String email;
    // Add other relevant fields as needed

    public UserInformation(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    

        // getUsername 方法
        public String getUsername() {
            return username;
        }

        // getName 方法
        public String getName() {
            return name;
        }

        // getEmail 方法
        public String getEmail() {
            return email;
        }
    }

