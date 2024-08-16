package com.projectmvc.service;
import com.projectmvc.model.*;


public interface UserService {
	
    void signUp(final User user);
    boolean signIn(final String emailId, final String password);
}
