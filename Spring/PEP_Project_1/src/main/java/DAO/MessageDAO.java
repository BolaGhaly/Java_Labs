package DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            Statement statement = connection.createStatement();
            ResultSet pkeyResultSet = statement.executeQuery(sql);

            while (pkeyResultSet.next()) {
                int message_id = (int) pkeyResultSet.getLong(1);
                int posted_by = (int) pkeyResultSet.getLong(2);
                String message_text = (String) pkeyResultSet.getString(3);
                int time_posted_epoch = (int) pkeyResultSet.getLong(4);
                allMessages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
            }

            return allMessages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message getMessageById(Integer id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet pkeyResultSet = preparedStatement.executeQuery();

            if (pkeyResultSet.next()) {
                int posted_by = (int) pkeyResultSet.getLong(2);
                String message_text = (String) pkeyResultSet.getString(3);
                int time_posted_epoch = (int) pkeyResultSet.getLong(4);
                return new Message(id, posted_by, message_text, time_posted_epoch);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesByAccountId(Integer id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessagesByUser = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet pkeyResultSet = preparedStatement.executeQuery();

            while (pkeyResultSet.next()) {
                int message_id = (int) pkeyResultSet.getLong(1);
                String message_text = (String) pkeyResultSet.getString(3);
                int time_posted_epoch = (int) pkeyResultSet.getLong(4);
                allMessagesByUser.add(new Message(message_id, id, message_text, time_posted_epoch));
            }

            return allMessagesByUser;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message createNewMessage(Message message) {
        // if the message_text is more than 255 characters long, return null
        if (message.message_text.length() > 255)
            return null;

        // if the message_text is blank, return null
        if (message.message_text.length() == 0)
            return null;

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int message_id = pkeyResultSet.getInt(1);
                int posted_by = message.getPosted_by();
                String message_text = message.getMessage_text();
                long time_posted_epoch = message.getTime_posted_epoch();
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message updateMessageById(Integer messageId, String newMessageText) {
        if (newMessageText.length() > 255)
            return null;
        if (newMessageText.length() == 0)
            return null;

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql1 = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, newMessageText);
            preparedStatement1.setInt(2, messageId);
            preparedStatement1.executeUpdate();

            String sql2 = "SELECT * FROM message WHERE message_text = ? AND message_id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, newMessageText);
            preparedStatement2.setInt(2, messageId);
            ResultSet pkeyResultSet = preparedStatement2.executeQuery();

            if (pkeyResultSet.next()) {
                int posted_by = pkeyResultSet.getInt(2);
                long time_posted_epoch = pkeyResultSet.getLong(4);
                return new Message(messageId, posted_by, newMessageText, time_posted_epoch);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessageById(Integer id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql1 = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, id);
            ResultSet pkeyResultSet = preparedStatement1.executeQuery();

            String sql2 = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();

            if (pkeyResultSet.next()) {
                int posted_by = pkeyResultSet.getInt(2);
                String message_text = pkeyResultSet.getString(3);
                long time_posted_epoch = pkeyResultSet.getLong(4);
                return new Message(id, posted_by, message_text, time_posted_epoch);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
