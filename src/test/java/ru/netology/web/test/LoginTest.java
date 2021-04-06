package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.BaseSQL;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    void shouldLoginValid() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoValid();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = BaseSQL.getVerificationCode();
        val verify = verificationPage.validVerify(verificationCode);
        verify.dashboardPageVisible();
    }

    @Test
    void shouldBlockedAfterThreePasswords() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoInvalid();
        loginPage.login(authInfo);
        loginPage.cleanLoginFields();
        loginPage.login(authInfo);
        loginPage.cleanLoginFields();
        loginPage.login(authInfo);
        val statusSQL = BaseSQL.getStatus(authInfo);
        assertEquals("blocked", statusSQL);
    }

    @AfterAll
    static void clean() {
        BaseSQL.cleanBase();
    }
}