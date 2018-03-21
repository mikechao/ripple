package com.mike.service;

import com.mike.User;

public interface UserService {

    /**
     * Check if a User exists
     * 
     * @param user
     *            - The user to see if it exists
     * @return - True if the User exists <br>
     *         Otherwise false
     */
    public boolean userExists(User user);

    /**
     * Set the current user
     * 
     * @param user
     */
    public void setCurrentUser(User user);

    /**
     * @return The current user
     */
    public User getCurrentUser();

    /**
     * Add a user from another server Entry point for currentUsersRoute
     * 
     * @see
     * @param user
     */
    public void addRemoteUser(User user);
}
