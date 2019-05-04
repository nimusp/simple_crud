/*
 * Developed by Sumin Pavel on 5/2/19 11:24 PM
 */

package myself.inc.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
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
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setServerTimezone("UTC");
                dataSource.setDatabaseName("my_database");
                dataSource.setUser(user);
                dataSource.setPassword(password);
                connection = dataSource.getConnection();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
