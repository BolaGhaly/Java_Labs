package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account createNewUser(Account account) {
        // if the password is less than 4 characters long, return null
        if (account.getPassword().length() < 4)
            return null;

        // if the username is blank, return null
        if (account.getUsername().length() == 0)
            return null;

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_user_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_user_id, account.getUsername(), account.getPassword());
            }
            // if an account with the same username already exists,
            // then log/print out (in Terminal) sql exception and return null
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account userLogin(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet pkeyResultSet = preparedStatement.executeQuery();

            if (pkeyResultSet.next()) {
                int user_id = (int) pkeyResultSet.getLong(1);
                return new Account(user_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
