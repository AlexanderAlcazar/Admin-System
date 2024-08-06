package edu.smc.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Performs operations for storing and retrieving student information
 */
public class Database {
    private static final String DATA_FILE_NAME = "data.txt";
    private Set<Student> studentList;

    /**
     * Constructor for initial database setup
     */
    public Database() {
        this.studentList = new HashSet<>();
    }

    /**
     * Adds a new Student to the database
     * @param firstname Student's first name
     * @param lastname Student's last name
     * @param phoneNumber Student's phone number
     * @param address Student's address
     * @param major Student's major
     * @return boolean value indicating the success of the operation
     */
    public boolean addStudent(String firstname, String lastname, String phoneNumber, String address, String major) {
        return studentList.add(new Student(firstname, lastname, generateID(), phoneNumber, address, major));
    }

    /**
     * Removes a Student from the database
     * @param studentID ID of the student to remove
     * @return boolean value indicating the success of the operation
     */
    public boolean removeStudent(int studentID){
        return studentList.removeIf(student -> student.getStudentID() == studentID);
    }

    /**
     * Retrieves a Student from the database
     * @param studentID ID of the student to retrieve
     * @return the retrieved Student object, null if not found
     */
    public Student getStudent(int studentID){
        for (Student student : studentList) {
            if (student.getStudentID() == studentID){
                return student;
            }
        }
        return null;
    }

    /**
     * Lists all Students in the database
     * @return a String representation of all Students
     */
    public String listStudents(){
        StringBuilder list = new StringBuilder();
        for (Student i : studentList) {
            list.append(i);
        }
        return list.toString();
    }

    /**
     * Verifies a Student's login credentials
     * @param username the Student's username
     * @param password the Student's password
     * @return boolean value indicating the validity of the credentials
     */
    public boolean verify(String username, String password) {
        for (Student student : studentList) {
            if (student.verify(username, password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a unique ID for a Student
     * @return the generated ID
     */
    private int generateID() {
        boolean IDExist;
        Random random = new Random();
        int randomNumber;
        do {
            IDExist = false;
            randomNumber = random.nextInt(9000000) + 1000000;
            for (Student student : studentList){
                if (student.getStudentID() == randomNumber) {
                    IDExist = true;
                    break;
                }
            }
        } while (IDExist);
        return randomNumber;
    }

    /**
     * Loads Student data from a file
     */
    public void loadData() {
        File dataFile = new File(DATA_FILE_NAME);
        try {
            Scanner scanner = new Scanner(dataFile);
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                studentList.add(parseData(data));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts an array of Strings into a Student object
     * @param data the array of Strings to convert
     * @return the created Student object
     */
    private Student parseData(String[] data) {
        String firstName = data[0];
        String lastName = data[1];
        int studentID = Integer.parseInt(data[2]);
        String phoneNumber = data[3];
        String address = data[4];
        String major = data[5];
        String username = data[6];
        String password = data[7];
        return new Student(firstName, lastName, studentID, phoneNumber, address, major, username, password);
    }

    /**
     * Saves Student data to a file
     */
    public void saveData() {
        File dataFile = new File(DATA_FILE_NAME);
        try {
            PrintWriter writer = new PrintWriter(dataFile);
            for (Student student : studentList) {
                writer.println(toDataRecord(student));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a Student object to a String
     * @param student the Student object to convert
     * @return the created String
     */
    private String toDataRecord(Student student) {
        return student.getFirstName() + "," + student.getLastName() + "," +
                student.getStudentID() + "," + student.getPhoneNumber() + "," +
                student.getAddress() + "," + student.getMajor() + "," +
                student.getUsername() + "," + student.getPassword();
    }
}