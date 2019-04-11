package com.glassdoor.test.intern.first.database;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * UserDatabaseImplTest Tester.
 */
public class UserDatabaseImplTest {
    private final UserDatabaseImpl underTest = UserDatabaseImpl.getInstance();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addNewUser_happy() {
        long userId = 100L;
        String userName = "Peter";
        String userAddress = "123 Westlake Ave Seattle WA";
        User user = underTest.addNewUser(userId, userName, userAddress);
        assertEquals(userId, user.getUserId());
        assertEquals(userName, user.getUserName());
        assertEquals(userAddress, user.getUserAddress());
    }

    @Test
    public void addNewUser_invalidUserId() {
        long userId = -1L;
        String userName = "Mike";
        String userAddress = "125 Westlake Ave Seattle WA";
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.addNewUser(userId, userName, userAddress);
    }

    @Test
    public void addNewUser_nullUserName() {
        long userId = 101L;
        String userName = null;
        String userAddress = "125 Westlake Ave Seattle WA";
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.addNewUser(userId, userName, userAddress);
    }

    @Test
    public void addNewUser_emptyUserName() {
        long userId = 102L;
        String userName = "";
        String userAddress = "125 Westlake Ave Seattle WA";
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.addNewUser(userId, userName, userAddress);
    }

    @Test
    public void addNewUser_nullUserAddress() {
        long userId = 103L;
        String userName = "Mike";
        String userAddress = null;
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.addNewUser(userId, userName, userAddress);
    }

    @Test
    public void addNewUser_emptyUserAddress() {
        long userId = 104L;
        String userName = "Mike";
        String userAddress = "";
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.addNewUser(userId, userName, userAddress);
    }

    @Test
    public void addNewUser_userAlreadyExists() {
        long userId = 1L;
        String userName = "ABC";
        String userAddress = "123 Westlake Ave Seattle WA";
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.addNewUser(userId, userName, userAddress);
    }

    @Test
    public void getUserById_happy() {
        User user = underTest.getUserById(1L);
        assertEquals(1L, user.getUserId());
        assertEquals("ABC", user.getUserName());
        assertEquals("123 Some Street, City Name, ST", user.getUserAddress());
    }

    @Test
    public void getUserById_invalidUserId() {
        thrown.expect(IllegalArgumentException.class);
        User user = underTest.getUserById(0L);
    }

    @Test
    public void getUserById_userNotFound() {
        User user = underTest.getUserById(100000L);
        assertNull(user);
    }
} 
