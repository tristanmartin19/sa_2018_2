package toolbox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class datahandler {

    public Connection connectToDB () throws SQLException, ClassNotFoundException {
        String Username = "root";
        String Pwd = "root";
        String URL = "jdbc:mysql://localhost/sa_shop_finder ";
        String BugfixURL = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                URL + BugfixURL , Username, Pwd);

        return connection;
    }

}
