package com.projectmvc.dao;
import com.projectmvc.model.User;
import java.util.Optional;


public interface UserDAO {
    void save(User user);
    Optional<User> findByEmailAddress(String emailAddress);
}
