/*
 * Developed by Sumin Pavel on 5/2/19 11:23 PM
 */

package myself.inc.dao;

import myself.inc.model.User;
import myself.inc.util.DbUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public void addUser(@NotNull User user) {
        String dbRequest = "INSERT INTO users (first_name, last_name, dob, email) VALUES (?, ?, ?, ?)";
        Object[] paramList = new Object[] { user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getEmail() };
        try (Connection dbConnection = DbUtil.getConnection();
             PreparedStatement statement = getStatementWithParams(dbConnection, dbRequest, paramList)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error on add " + user);
            e.printStackTrace();
        }
    }

    public void updateUser(@NotNull User user) {
        String dbRequest = "UPDATE users SET first_name = ?, last_name = ?, dob = ?, email = ? WHERE user_id = ?";
        Object[] paramList = new Object[] { user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getUserId()};
        try (Connection dbConnection = DbUtil.getConnection();
             PreparedStatement statement = getStatementWithParams(dbConnection, dbRequest, paramList)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error on update " + user);
            e.printStackTrace();
        }
    }

    public void deleteUser(@NotNull User user) {
        String dbRequest = "DELETE FROM users WHERE user_id = ?";
        try (Connection dbConnection = DbUtil.getConnection();
             PreparedStatement statement = getStatementWithParams(dbConnection, dbRequest, user.getUserId())) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error on delete " + user);
            e.printStackTrace();
        }
    }

    @NotNull
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection dbConnection = DbUtil.getConnection();
             ResultSet resultSet = dbConnection.createStatement().executeQuery(
                "SELECT * FROM users"
        )) {
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("dob"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error on get all users");
            e.printStackTrace();
        }
        return list;
    }

    @Nullable
    public User getUserById(int userId) {
        User user = null;
        String dbRequest = "SELECT * FROM users WHERE user_id = ?";
        try (Connection dbConnection = DbUtil.getConnection();
             ResultSet resultSet = getStatementWithParams(dbConnection, dbRequest, userId).executeQuery()) {
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("dob"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error on get user by id " + userId);
            e.printStackTrace();
        }
        return user;
    }

    private PreparedStatement getStatementWithParams(@NotNull Connection connection, String sql, @NotNull Object... objects) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            statement.setObject(i + 1, objects[i]);
        }
        return statement;
    }
}
