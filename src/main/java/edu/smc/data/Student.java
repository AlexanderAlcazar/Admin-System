package edu.smc.data;

import edu.smc.base.Person;
import edu.smc.base.User;

import java.util.Objects;

/**
 * Represents a Student, which is a specific type of User.
 * Implements the Person interface for personal information.
 */
public class Student extends User implements Person {
    private String firstName;
    private String lastName;
    private int studentID;
    private String major;
    private String phoneNumber;
    private String address;
    private static final String DEFAULT_PASSWORD = "student";

    /**
     * Constructs a new Student with the specified details.
     *
     *  Used only when student is added manually via application.
     *
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param studentID The student ID.
     * @param phoneNumber The phone number of the student.
     * @param address The address of the student.
     * @param major The major of the student.
     */
    public Student(String firstName, String lastName, int studentID, String phoneNumber, String address, String major) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.major = major;
        setUsername(generateStudentEmail(studentID));
        setPassword(DEFAULT_PASSWORD);
    }
    /**
     * Constructs a new Student with the specified details.
     *
     * Used only when data is being loaded from a file.
     *
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param studentID The student ID.
     * @param phoneNumber The phone number of the student.
     * @param address The address of the student.
     * @param major The major of the student.
     * @param username The major of the student.
     * @param password The major of the student.
     */
    public Student(String firstName, String lastName, int studentID, String phoneNumber, String address, String major, String username, String password) {
        this(firstName, lastName, studentID, phoneNumber, address, major);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Generates an email address for the student based on their student ID.
     *
     * @param studentID The student ID.
     * @return The generated email address.
     */
    private String generateStudentEmail(int studentID) {
        return Integer.toString(studentID) + "@smc.edu";
    }

    /**
     * Gets the student ID.
     *
     * @return The student ID.
     */
    public int getStudentID() {
        return studentID;
    }


    /**
     * Retrieves First name of the object.
     * @return a String representing first name.
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new First name for the object.
     * @param firstName a String representing first name.
     */
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves Last name of the object.
     * @return a String representing last name.
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new Last name for the object.
     * @param lastName a String representing last name.
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves Phone Number of the object.
     * @return a String representing phone number.
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets a new Phone Number for the object.
     * @param phoneNumber a String representing phone number.
     */
    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves Address of the object.
     * @return a String representing address.
     */
    @Override
    public String getAddress() {
        return address;
    }

    /**
     * Sets a new Address for the object.
     * @param address a String representing address.
     */
    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the major of the student.
     *
     * @return The major of the student.
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the major of the student.
     *
     * @param major The new major of the student.
     */
    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return   "[ "+ firstName + ", " + lastName + ", " + studentID +
                ", " + phoneNumber +", " + address + ", "+ major + " ]" + "#";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return studentID == student.studentID &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(major, student.major) &&
                Objects.equals(phoneNumber, student.phoneNumber) &&
                Objects.equals(address, student.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, studentID, major, phoneNumber, address);
    }
}
