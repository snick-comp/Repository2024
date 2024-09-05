package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.dao.UserDAO;
import com.projectmvc.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public final class UserDAOImpl implements UserDAO {

    private static final UserDAOImpl INSTANCE = new UserDAOImpl();

    private UserDAOImpl() {}

    public static UserDAOImpl getInstance() {
        return INSTANCE;
    }

    public void save(final User user) {
        final String query = "INSERT INTO Users (name, email_address, mobile_number, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmailAddress());
            statement.setString(3, user.getMobileNumber());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    public Optional<User> findByEmailAddress(final String emailAddress) {
        final String query = "SELECT * FROM Users WHERE email_address = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, emailAddress);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmailAddress(resultSet.getString("email_address"));
                    user.setMobileNumber(resultSet.getString("mobile_number"));
                    user.setPassword(resultSet.getString("password"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email address", e);
        }
        return Optional.empty();
    }
}
