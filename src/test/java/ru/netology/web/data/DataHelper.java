package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static AuthInfo getAuthInfoValid() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoInvalid() {
        Faker faker = new Faker();
        return new AuthInfo("petya", faker.internet().password());
    }
}