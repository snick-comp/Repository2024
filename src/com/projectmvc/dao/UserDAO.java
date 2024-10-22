package com.projectmvc.dao;
import com.projectmvc.model.User;
import java.util.Collection;


public interface UserDAO {

    void addUser(final User user);

    User getUserByEmailAndPassword(final String emailAddress, final String password);

    Collection<User> getAllUsers();
    
    Collection<String> getSongListeningActivityToday(final Long userId);
    
    Collection<String> getSongStartAndEndTimeToday(final Long userId);
}
