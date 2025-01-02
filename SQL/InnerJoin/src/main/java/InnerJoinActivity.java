
import Util.ConnectionUtil;
import Util.FileUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

public class InnerJoinActivity {
    public Set<AbstractMap.SimpleEntry<Integer, String>> problem1() {
        String sql = FileUtil.parseSQLFile("problem1.sql");

        Set<AbstractMap.SimpleEntry<Integer, String>> results = new HashSet<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                results.add(new AbstractMap.SimpleEntry<>(
                        resultSet.getInt("id"),
                        resultSet.getString("student_name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("problem1: " + e.getMessage() + '\n');
        }

        return results;
    }
}
