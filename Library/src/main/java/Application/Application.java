package Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Application.Util.ConnectionUtil;

public class Application {
    public static void databaseSetup() {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("drop table if exists book");
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("drop table if exists author");
            ps2.executeUpdate();
            PreparedStatement ps3 = conn.prepareStatement("create table author("
                    + "id int primary key auto_increment, "
                    + "name varchar(255)); ");
            ps3.executeUpdate();
            PreparedStatement ps4 = conn.prepareStatement("create table book("
                    + "isbn int primary key, "
                    + "author_id int, "
                    + "title varchar(255), "
                    + "copies_available int, "
                    + "foreign key (author_id) references author(id));");
            ps4.executeUpdate();
            PreparedStatement ps5 = conn.prepareStatement(
                    "insert into author (name) values "
                    + "('jorge luis borges'),"
                    + "('italo calvino'),"
                    + "('thomas pynchon'),"
                    + "('marshall mcluhan'),"
                    + "('immanuel kant')");
            ps5.executeUpdate();
            PreparedStatement ps6 = conn.prepareStatement(
                    "insert into book (isbn, author_id, title, copies_available) values "
                    + "( 100, 1, 'ficciones', 2),"
                    + "(101, 1, 'book of sand', 0),"
                    + "(102, 2, 'mr palomar', 1),"
                    + "(103, 2, 'invisible cities', 3),"
                    + "(104, 3, 'crying of lot 49', 0),"
                    + "(105, 3, 'mason and dixon', 0),"
                    + "(106, 4, 'understanding media', 1),"
                    + "(107, 5, 'critique of pure reason', 7);");
            ps6.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
