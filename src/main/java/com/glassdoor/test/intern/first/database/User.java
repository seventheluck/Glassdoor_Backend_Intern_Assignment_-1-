package com.glassdoor.test.intern.first.database;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class User {

    private final long userId;
    private final String userName;
    private final String userAddress;

    public User(long userId, String userName, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("userAddress", userAddress)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equal(userName, user.userName) &&
                Objects.equal(userAddress, user.userAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, userName, userAddress);
    }
}
