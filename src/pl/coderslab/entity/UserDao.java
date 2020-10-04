package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id=?";
    private static final String READ_NAME_USER_QUERY =
            "SELECT * FROM users WHERE username=?";
    private static final String READ_EMAIL_USER_QUERY =
            "SELECT * FROM users WHERE email=?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username=?, email=?, password=? WHERE id=?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM tableName WHERE id= ?";
    private static final String FIND_ALL_USERS_QUERY=
            "SELECT * FROM users";

    public  String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public  User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet rS = statement.getGeneratedKeys();
            if (rS.next()) {
                user.setId(rS.getInt(1));
            }
            return user;
        } catch (SQLIntegrityConstraintViolationException ez) {
            System.out.println("Już jest taki email");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public  User readId(int usrId) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, usrId);
            ResultSet rS = statement.executeQuery();

            while (rS.next()) {
                User user = new User();
                user.setId(rS.getInt("id"));
                user.setEmail(rS.getString("email"));
                user.setUserName(rS.getString("username"));
                user.setPassword(rS.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public  User readUserName(String userName) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(READ_NAME_USER_QUERY);
            statement.setString(1, userName);
            ResultSet rS = statement.executeQuery();

            while (rS.next()) {
                User user = new User();
                user.setId(rS.getInt("id"));
                user.setEmail(rS.getString("email"));
                user.setUserName(rS.getString("username"));
                user.setPassword(rS.getString("password"));
                return user;
            }
        } catch (SQLException ez) {
            ez.printStackTrace();
        }
        return null;
    }
    public  User readUserEmail(String userEmail) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(READ_EMAIL_USER_QUERY);
            statement.setString(1, userEmail);
            ResultSet rS = statement.executeQuery();

            while (rS.next()) {
                User user = new User();
                user.setId(rS.getInt("id"));
                user.setEmail(rS.getString("email"));
                user.setUserName(rS.getString("username"));
                user.setPassword(rS.getString("password"));
                return user;
            }
        } catch (Exception ez) {
            ez.printStackTrace();

        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);

                statement.setInt(4, user.getId());
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getEmail());
                statement.setString(3, this.hashPassword(user.getPassword()));
                statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void delete(String tableName, int userId) {
        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY.replace("tableName", tableName));
            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

    public void printData2(Connection conn, String query, String... columnNames) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                for (String param : columnNames) {
                    System.out.println(resultSet.getString(param) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(Connection conn, String query, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
