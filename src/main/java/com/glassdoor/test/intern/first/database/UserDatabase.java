package com.glassdoor.test.intern.first.database;

import lombok.NonNull;

public interface UserDatabase {
    /**
     * Add a new user by userId, userName, and userAddress.
     * This method will return the added user.
     * If the userId already exists, the method will throw exception.
     *
     * @param userId      the userId, must be greater than 0.
     * @param userName    the user name, cannot be empty.
     * @param userAddress the user address, cannot be empty.
     * @return the added user.
     */
    User addNewUser(final long userId, @NonNull final String userName, @NonNull final String userAddress);

    /**
     * Get user from database by userId.
     * If user not exists, it will return null.
     *
     * @param userId the userId, must be greater than 0.
     * @return the user if found.
     */
    User getUserById(final long userId);
}