// Declaring package of the class
package edu.smc.base;

// Importing required utility class
import java.util.Objects;

/**
 * Declaring an abstract User class which represents a general user with a username and password.
 */
public abstract class User {

    // Declaring instance variables for username and password
    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Parameterized constructor accepting username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Method to get the username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to set the username.
     *
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to get the password.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to set the password.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to verify given username and password with the user's username and password.
     *
     * @param username The username to verify.
     * @param password The password to verify.
     * @return true if the given username and password match the user's username and password, false otherwise.
     */
    public boolean verify(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }


    /**
     * Overriding equals() method for user object comparison based on username and password.
     *
     * @param o The object to compare with.
     * @return true if the given object is equal to this user, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                password.equals(user.password);
    }

    /**
     * Overriding hashCode() method to provide a unique hash value based on username and password.
     *
     * @return The hash code of the user.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
