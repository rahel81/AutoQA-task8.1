package ru.netology.web.data;

import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseSQL {

    private BaseSQL() {
    }

    public static Connection getConnection() throws SQLException {
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        return connection;
    }

    public static String getVerificationCode() {
        try (val conn = getConnection();
             val countStmt = conn.createStatement()) {
            val dataSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
            val resultSet = countStmt.executeQuery(dataSQL);
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }

    public static String getStatus(DataHelper.AuthInfo authInfo) {
        String statusSQL = "SELECT status FROM users WHERE login=?";
        try (val conn = getConnection();
             val prepareStmt = conn.prepareStatement(statusSQL)) {
            prepareStmt.setString(1, authInfo.getLogin());
            val resultSet = prepareStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static void cleanBase() {
        String deleteCards = "DELETE FROM cards; ";
        String deleteAuthCodes = "DELETE FROM auth_codes; ";
        String deleteUsers = "DELETE FROM users; ";
        try (val conn = BaseSQL.getConnection();
             val deleteCardsStmt = conn.createStatement();
             val deleteAuthCodesStmt = conn.createStatement();
             val deleteUsersStmt = conn.createStatement()
        ) {
            deleteCardsStmt.executeUpdate(deleteCards);
            deleteAuthCodesStmt.executeUpdate(deleteAuthCodes);
            deleteUsersStmt.executeUpdate(deleteUsers);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
