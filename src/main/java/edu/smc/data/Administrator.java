package edu.smc.data;

import edu.smc.base.User;

/**
 * Represents an Administrator derived from a User.
 */
public class Administrator extends User {

    /**
     * Creates a new Administrator with the provided username and password.
     *
     * @param username the username of the Administrator.
     * @param password the password for the Administrator.
     */
    public Administrator(String username, String password) {
        super(username, password);
    }
}