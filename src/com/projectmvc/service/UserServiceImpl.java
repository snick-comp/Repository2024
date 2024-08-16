package com.projectmvc.service;
import com.projectmvc.model.*;
import java.util.ArrayList;
import java.util.Collection;


public final class UserServiceImpl implements UserService {
	
    private final Collection<User> users = new ArrayList<>();

    private static final UserServiceImpl INSTANCE = new UserServiceImpl();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    public void signUp(final User user) {
        users.add(user);
    }

    public boolean signIn(final String emailAddress, final String password) {
        for (User user : users) {
            if (user.getEmailAddress().equals(emailAddress) && user.getPassword().equals(password)) {
                return false;
            }
        }
        return false;
    }
}