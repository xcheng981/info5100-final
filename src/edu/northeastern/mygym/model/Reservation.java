package edu.northeastern.mygym.model;

public class Reservation {
    private int reservationId;
    private String courseCode;
    private String courseName;
    private String userName;

    public Reservation(int reservationId, String courseCode, String courseName, String userName) {
        this.reservationId = reservationId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.userName = userName;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        // Customize the string representation for display
        return "Reservation Id: " + reservationId +
               ", Course Code: " + courseCode +
               ", Course Name: " + courseName +
               ", User Name: " + userName;
    }
}
