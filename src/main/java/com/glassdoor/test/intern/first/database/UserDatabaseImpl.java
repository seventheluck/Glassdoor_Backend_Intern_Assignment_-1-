package com.glassdoor.test.intern.first.database;

import com.glassdoor.test.intern.first.metrics.Metrics;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * An in-memory implementation of {@link UserDatabase}.
 */
public class UserDatabaseImpl implements UserDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDatabaseImpl.class);

    private static final String USER_DB_FILE_RESOURCE_NAME = "user_database.txt";

    //
    // I change this class initialization to make it singleton (eager).
    // This is because I think this component is the key component for the system.
    // We should make it highly accessible, thread-safe, and low resource cost.
    // And I decide to make it eager rather than lazy, since if anything wrong with this key component,
    // I want the system fail immediately, instead of fail at runtime.
    //
    private static final UserDatabaseImpl INSTANCE = new UserDatabaseImpl();
    /**
     * This is a simple in-memory map maintaining the map from userId to user object.
     * It is used to replace the old version of code (userNames and addresses).
     */
    private final ConcurrentMap<Long, User> users;

    //
    // I add metrics here just want to demo how I use it.
    // Maybe a bit overkill
    //
    private final Metrics metrics = Metrics.nullMetrics();

    private UserDatabaseImpl() {

        // I delete the old readDB() method, because it is only used once by the constructor.
        // it doesn't make much sense to define a separate public method for it.
        // So all initialization work will be in this constructor.
        //
        // Ideally the data in the database should be all valid.
        // But to make the system more robust, I want this key component can tolerant bad
        // data entries: in system initialization, it will skip the wrong-formatted data entries.
        // But if there are too many bad data entries, properly something really bad happened,
        // I also don't want to fail the system initialization in this case.
        // So I set up a threshold that if 50% of entries from the file are bad, the initialization
        // would failed.

        Map<Long, User> tmpUsers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                UserDatabase.class.getClassLoader().getResourceAsStream(USER_DB_FILE_RESOURCE_NAME)))) {
            String line;
            int totalRawLineCount = 0;
            while ((line = br.readLine()) != null) {
                totalRawLineCount++;
                User user = parseUser(line);
                if (user != null) {
                    if (!tmpUsers.containsKey(user.getUserId())) {
                        tmpUsers.put(user.getUserId(), user);
                    } else {
                        LOGGER.warn("User already exists. Existing user: {}. New user {}", tmpUsers.get(user.getUserId()), user);
                    }
                } else {
                    LOGGER.warn("Found an invalid data entry: {}", line);
                }
            }

            // Fail the system if more than half of the data are invalid.
            if (tmpUsers.size() < totalRawLineCount / 2) {
                throw new IllegalStateException("More than half data are invalid. Something really bad. Please check the file DB at " + USER_DB_FILE_RESOURCE_NAME);
            }
            this.users = new ConcurrentHashMap<>(tmpUsers);
            LOGGER.info("Totally load {} users.", this.users.size());
        } catch (Exception e) {
            LOGGER.error("Failed to initialize DB.", e);
            throw new IllegalStateException(e);
        }
    }
    // Comment the old version of map, and make it thread-safe by using ConcurrentMap.
//    public Map<Integer, String> userNames = new HashMap<>();
//    public Map<Integer, String> addresses = new HashMap<>();

    /**
     * Returns the singleton instance of the {@link UserDatabaseImpl}. It is thread-safe.
     */
    public static UserDatabaseImpl getInstance() {
        return INSTANCE;
    }

    /**
     * Parse a string line ( read from DB) into a User object.
     * If this is not a valid entry, it will return null.
     */
    private static User parseUser(@NonNull final String line) {
        // We assume the data is in the format of
        // [id] [name]  [address]
        // Example:
        // 1	ABC	    123 Some Street, City Name, ST
        String splits[] = line.split("\t");
        if (splits.length != 3) {
            return null;
        }
        Long userId = Longs.tryParse(splits[0]);
        String userName = splits[1];
        String userAddress = splits[2];
        try {
            validateUserId(userId);
            validateUserName(userName);
            validateUserAddress(userAddress);
        } catch (IllegalArgumentException e) {
            // Return null since we tolerant bad data.
            return null;
        }

        return new User(userId, userName, userAddress);
    }

    private static void validateUserId(@NonNull final Long userId) {
        Preconditions.checkArgument(userId != null, "User ID is required");
        Preconditions.checkArgument(userId > 0, "User ID must be greater than 0. But it is " + userId);
    }

    private static void validateUserName(@NonNull final String userName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(userName), "User Name is required");
    }

    private static void validateUserAddress(@NonNull final String userAddress) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(userAddress), "User address cannot be empty");
    }

    // NOTE this method only save the new users into the in-memory DB, but not sync into the file system.
    // So when the system shutdown, the newly added users will be lost.
    // To simplify the system, I assume this is acceptable.
    @Override
    public User addNewUser(final long userId, @NonNull final String userName, @NonNull final String userAddress) {
        validateUserId(userId);
        validateUserName(userName);
        validateUserAddress(userAddress);
        metrics.addCount("addUserName", 1);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            User user = new User(userId, userName, userAddress);
            User toReturn = users.putIfAbsent(userId, user);
            if (toReturn == null) {
                metrics.addCount("addUserName.success", 1);
                return user;
            }
            metrics.addCount("addUserName.error", 1);
            throw new IllegalArgumentException("User Id " + userId + " already exists. You cannot create one with the same id");
        } finally {
            metrics.addTimer("addUserName", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

    @Override
    public User getUserById(final long userId) {
        validateUserId(userId);
        metrics.addCount("getUserById", 1);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            User user = users.get(userId);
            metrics.addCount("getUserById:" + (user != null ? "success" : "notFound"), 1);
            return user;
        } finally {
            metrics.addTimer("getUserById", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}