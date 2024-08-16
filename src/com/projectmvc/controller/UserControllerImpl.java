package com.projectmvc.controller;
import com.projectmvc.service.*;
import com.projectmvc.model.*;


public final class UserControllerImpl implements UserController {
	
    private final UserService userService = UserServiceImpl.getInstance();

    private static final UserControllerImpl INSTANCE = new UserControllerImpl();

    private UserControllerImpl() {}

    public static UserControllerImpl getInstance() {
        return INSTANCE;
    }

    public void signUp(final User user) {
        userService.signUp(user);
    }

    public boolean signIn(final String emailAddress, final String password) {
        return userService.signIn(emailAddress, password);
    }
}
