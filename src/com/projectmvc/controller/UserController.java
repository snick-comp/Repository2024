package com.projectmvc.controller;
import com.projectmvc.model.*;


public interface UserController {
	
    void signUp(final User user);
    boolean signIn(final String emailId, final String password);
}
