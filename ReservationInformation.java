package edu.northeastern.mygym;

public class ReservationInformation {
    private int reservationID;
    private String courseCode;
    private String courseName;
    private String userName;

    public ReservationInformation(int reservationID, String courseCode, String courseName, String userName) {
        this.reservationID = reservationID;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.userName = userName;
    }

    public int getReservationID() {
        return reservationID;
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
        return "Reservation ID: " + reservationID +
               ", Course Code: " + courseCode +
               ", Course Name: " + courseName +
               ", User Name: " + userName;
    }
}
