package com.glassdoor.test.intern.first.database;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User Tester.
 */
public class UserTest {

    private static User user;

    @BeforeClass
    public static void prepare() {
        user = User.builder()
                .userId(100L)
                .userAddress("123 Westlake Ave Seattle WA")
                .userName("Peter").build();
    }

    @Test
    public void testToString() {
        assertEquals("User(userId=100, userName=Peter, userAddress=123 Westlake Ave Seattle WA)", user.toString());
    }

    @Test
    public void testEquals_happy() {
        User user_copy = User.builder()
                .userId(100L)
                .userAddress("123 Westlake Ave Seattle WA")
                .userName("Peter").build();
        assertEquals(user, user_copy);
    }

    @Test
    public void testEquals_notEqual() {
        User user_copy = User.builder()
                .userId(100L)
                .userAddress("123 Westlake Ave Seattle WA")
                .userName("peter").build();
        assertNotEquals(user, user_copy);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() {
        User user_copy = User.builder()
                .userId(100L)
                .userAddress("123 Westlake Ave Seattle WA")
                .userName("Peter").build();
        assertEquals(user.hashCode(), user_copy.hashCode());
    }
} 
