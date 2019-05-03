/*
 * Developed by Sumin Pavel on 5/2/19 11:24 PM
 */

package myself.inc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            Properties properties = new Properties();
            InputStream stream = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
            try {
                properties.load(stream);
                String driver = properties.getProperty("driver");
                String url = properties.getProperty("url");
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                DriverManager.registerDriver((Driver) Class.forName(driver).newInstance());
                connection = DriverManager.getConnection(url, user, password);
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
