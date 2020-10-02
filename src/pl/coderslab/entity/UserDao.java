package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id=?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username=?, email=?, password=? WHERE id=?";
    private static final String DELETE_USER_QUERY =
            "DELETE * FROM users WHERE id= ?";

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLIntegrityConstraintViolationException ez) {
            System.out.println("Już jest taki email");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(int usrId) {

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

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
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
