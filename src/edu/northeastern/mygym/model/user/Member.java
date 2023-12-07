package edu.northeastern.mygym.model.user;

import edu.northeastern.mygym.database.DatabaseHelper;
import edu.northeastern.mygym.model.Course;
import edu.northeastern.mygym.model.Reservation;

import java.sql.SQLException;
import java.util.List;

public class Member extends User {

    public Member(String userName, String name, String email) {
        super(userName, name, email);
    }

    public Member(Profile profile) {
        super(profile);
    }

    public List<Course> listFirst10Courses() throws SQLException {
        return DatabaseHelper.getFirst10Courses();
    }

    public List<Reservation> getReservations() throws SQLException {
        return DatabaseHelper.getUserReservations(getUserName());
    }

    public boolean reserveCourse(String courseCode) throws SQLException {
        return DatabaseHelper.reserveCourse(getUserName(), courseCode);
    }

    public void cancelReservation(int reservationId) throws SQLException {
        DatabaseHelper.cancelReservation(reservationId);
    }
}