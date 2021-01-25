package utilities;

import java.sql.*;

public class Connection_Util {
    Connection connection = null;
    public static Connection connect_to_DB(String user, String password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost/army", user, password);
            return connection1;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
