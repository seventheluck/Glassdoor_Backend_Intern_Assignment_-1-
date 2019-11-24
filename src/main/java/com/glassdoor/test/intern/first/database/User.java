package com.glassdoor.test.intern.first.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {

    private final long userId;
    private final String userName;
    private final String userAddress;

}
