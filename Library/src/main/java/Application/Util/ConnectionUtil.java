package Application.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The ConnectionUtil class will be utilized to create an active connection to
 * our database. This class utilizes the
 * singleton design pattern. We will be utilizing an in-memory called
 * h2database. In-memory means that the database
 * is dissolved when the program ends - it is only for use in testing. Do not
 * change anything in this class.
 */
public class ConnectionUtil {
    private static String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static String username = "sa";
    private static String password = "sa";

    private static Connection connection = null;

    /**
     * @return active connection to the database
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

}
