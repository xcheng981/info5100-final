package edu.northeastern.mygym.model.user;

import edu.northeastern.mygym.database.DatabaseHelper;
import edu.northeastern.mygym.model.Reservation;

import java.sql.SQLException;
import java.util.List;

public class Admin extends User {

    public Admin(String userName, String name, String email) {
        super(userName, name, email);
    }

    public Admin(Profile profile) {
        super(profile);
    }

    public List<Reservation> listAllReservations() throws SQLException {
        return DatabaseHelper.getAllReservations();
    }

    public List<User> listAllMembers() throws SQLException {
        return DatabaseHelper.getMembers();
    }

    public void addMember(final String username, final String name, final String email) throws SQLException {
        DatabaseHelper.addMember(username, name, email);
    }

    public boolean isUsernameDuplicated(String username) throws SQLException {
        return DatabaseHelper.isUsernameDuplicated(username);
    }

    public User getUserInfoByUserName(String username) throws SQLException {
        return DatabaseHelper.getUserInfoByUsername(username);
    }

    public void deleteMember(String username) throws SQLException {
        DatabaseHelper.deleteMember(username);
    }

    public void updateMember(String username, String name, String email) throws SQLException {
        DatabaseHelper.updateMember(username, name, email);
    }
}