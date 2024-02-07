package com.moayo.moayoeats.test;

import com.moayo.moayoeats.backend.domain.user.entity.User;

public interface CommonTest {

    Long TEST_USER_ID = 1L;

    String TEST_USER_EMAIL = "username@gmail.com";
    String TEST_USER_PASSWORD = "Password123!";
    String TEST_USER_CHECK_PASSWORD = "Password123!";
    String TEST_USER_NICKNAME = "사용자1";

    User TEST_USER = User.builder()
        .email(TEST_USER_EMAIL)
        .password(TEST_USER_PASSWORD)
        .nickname(TEST_USER_NICKNAME)
        .build();


}
