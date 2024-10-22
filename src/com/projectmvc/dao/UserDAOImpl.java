package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.exception.CustomDatabaseException;
import com.projectmvc.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public final class UserDAOImpl implements UserDAO {

    public void addUser(final User user) {
       
    	final String query = "INSERT INTO users (name, email_address, mobile_number, password) VALUES (?, ?, ?, ?)";
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            connection.setAutoCommit(false);
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmailAddress());
                statement.setString(3, user.getMobileNumber());
                statement.setString(4, user.getPassword());
                statement.executeUpdate();
            }
            connection.commit();
       
        } catch (final SQLException e) {
            if (connection != null) {
              
            	try {
                    connection.rollback();
                
            	} catch (final SQLException rollbackEx) {
                 
            		throw new CustomDatabaseException("Error rolling back transaction", rollbackEx);
                }
            }
          
            throw new CustomDatabaseException("Error adding user", e);
       
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    public User getUserByEmailAndPassword(final String emailAddress, final String password) {
       
    	final String query = "SELECT * FROM users WHERE email_address = ? AND password = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, emailAddress);
                statement.setString(2, password);
               
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        final User user = new User();
                        user.setId(resultSet.getLong("id"));
                        user.setName(resultSet.getString("name"));
                        user.setEmailAddress(resultSet.getString("email_address"));
                        user.setMobileNumber(resultSet.getString("mobile_number"));
                        user.setPassword(resultSet.getString("password"));
                        return user;
                    }
                }
            }
        } catch (final SQLException e) {
            
        	throw new CustomDatabaseException("Error getting user by email and password", e);
      
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return null;
    }

    public Collection<User> getAllUsers() {
       
    	final Collection<User> users = new ArrayList<>();
        final String query = "SELECT * FROM users";
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
           
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmailAddress(resultSet.getString("email_address"));
                    user.setMobileNumber(resultSet.getString("mobile_number"));
                    user.setPassword(resultSet.getString("password"));
                    users.add(user);
                }
            }
       
        } catch (final SQLException e) {
           
        	throw new CustomDatabaseException("Error getting all users", e);
        
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return users;
    }
    
    public Collection<String> getSongListeningActivityToday(final Long userId) {
        
    	final String query = "SELECT song_name FROM song_listening WHERE user_id = ? AND DATE(timestamp_listened) = CURDATE()";
    	Collection<String> songsListenedToday = new ArrayList<>();
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        songsListenedToday.add(resultSet.getString("song_name"));
                    }
                }
            }

        } catch (final SQLException e) {
            throw new CustomDatabaseException("Error getting songs listened to by user today", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return songsListenedToday;
    }
    public Collection<String> getSongStartAndEndTimeToday(final Long userId) {
        
    	final String query = "SELECT start_time, end_time FROM song_listening WHERE user_id = ? AND DATE(timestamp_listened) = CURDATE()";
    	Collection<String> times = new ArrayList<>();
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String startTime = resultSet.getTime("start_time").toString();
                        String endTime = resultSet.getTime("end_time").toString();
                        times.add("Start: " + startTime + ", End: " + endTime);
                    }
                }
            }

        } catch (final SQLException e) {
          
        	throw new CustomDatabaseException("Error getting song start and end times for user today", e);
       
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return times;
    }    
}
