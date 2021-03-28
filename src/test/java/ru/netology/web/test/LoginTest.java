package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.BaseSQL;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    BaseSQL mySQL = new BaseSQL();

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    void shouldLoginValid() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoValid();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = BaseSQL.getVerificationCode(authInfo.getLogin());
        val verify = verificationPage.validVerify(verificationCode);
        verify.dashboardPageVisible();
    }

    @Test
    void shouldBlockedAfterThreePasswords() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoInvalid();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginFields();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginFields();
        loginPage.validLogin(authInfo);
        val statusSQL = mySQL.getStatus(authInfo.getLogin());
        assertEquals("blocked", statusSQL);
    }

    @AfterAll
    static void clean() throws SQLException {
        BaseSQL.cleanBase();
    }
}