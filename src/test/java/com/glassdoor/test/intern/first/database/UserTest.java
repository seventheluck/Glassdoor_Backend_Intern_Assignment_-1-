package com.glassdoor.test.intern.first.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User Tester.
 */
public class UserTest {

    @Test
    public void testToString() {
        long userId = 100L;
        String userName = "Peter";
        String userAddress = "123 Westlake Ave Seattle WA";
        User user = new User(userId, userName, userAddress);
        assertEquals("User{userId=100, userName=Peter, userAddress=123 Westlake Ave Seattle WA}", user.toString());
    }

    @Test
    public void testEquals_happy() {
        long userId = 100L;
        String userName = "Peter";
        String userAddress = "123 Westlake Ave Seattle WA";
        User user = new User(userId, userName, userAddress);
        User user_copy = new User(userId, userName, userAddress);
        assertEquals(user, user_copy);
    }

    @Test
    public void testEquals_notEqual() {
        long userId = 100L;
        String userName = "Peter";
        String userAddress = "123 Westlake Ave Seattle WA";
        User user = new User(userId, userName, userAddress);
        User user_copy = new User(userId, "peter", userAddress);
        assertNotEquals(user, user_copy);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() {
        long userId = 100L;
        String userName = "Peter";
        String userAddress = "123 Westlake Ave Seattle WA";
        User user = new User(userId, userName, userAddress);
        User user_copy = new User(userId, userName, userAddress);
        assertEquals(user.hashCode(), user_copy.hashCode());
    }
} 
