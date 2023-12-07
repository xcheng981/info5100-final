package edu.northeastern.mygym.model.user;

import edu.northeastern.mygym.database.DatabaseHelper;
import edu.northeastern.mygym.model.Reservation;

import java.sql.SQLException;
import java.util.List;

public class Admin extends User {
    private static final DatabaseHelper DATABASE_HELPER = new DatabaseHelper();

    public Admin(String userName, String name, String email) {
        super(userName, name, email);
    }

    public Admin(Profile profile) {
        super(profile);
    }

    public List<Reservation> listAllReservations() throws SQLException {
        return DATABASE_HELPER.getAllReservations();
    }

    public List<User> listAllMembers() throws SQLException {
        return DATABASE_HELPER.getMembers();
    }

    public void addMember(final String username, final String name, final String email) throws SQLException {
        DATABASE_HELPER.addMember(username, name, email);
    }

    public boolean isUsernameDuplicated(String username) throws SQLException {
        return DATABASE_HELPER.isUsernameDuplicated(username);
    }

    public User getUserInfoByUserName(String username) throws SQLException {
        return DATABASE_HELPER.getUserInfoByUsername(username);
    }

    public void deleteMember(String username) throws SQLException {
        DATABASE_HELPER.deleteMember(username);
    }

    public void updateMember(String username, String name, String email) throws SQLException {
        DATABASE_HELPER.updateMember(username, name, email);
    }
}