package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Statement statement = null;
        String sql = "CREATE TABLE if not exists user (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";

        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
//            if (connection != null) {
//                connection.close();
//            }
            if (statement != null) {
                statement.close();
            }
        }

    }

    public void dropUsersTable() throws SQLException {
        String sqlDropTable = "drop table user";
        String sqlCheckTable = "show tables";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCheckTable);

            while (resultSet.next()) {
                sqlCheckTable = resultSet.getString("Tables_in_jdbctesttable");
            }
            if (sqlCheckTable.equals("user")) {
                statement.execute(sqlDropTable);
                System.out.println("table deleted");
            } else System.out.println("table does not exist");

        } catch (SQLException ignored) {
            System.err.println("error - table does not exist");
        }
        finally {
//            if (connection != null) {
//                connection.close();
//            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlAddUser = "insert into user (name, lastname, age) values (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAddUser)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlRemoveById = "delete from user where (id=?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveById)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        String sql = "select id, name, lastname, age from user";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        String sqlDeleteTable = "delete from user";

        try ( Statement statement = connection.createStatement() ){
            statement.executeUpdate(sqlDeleteTable);
            statement.executeUpdate("truncate table user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
