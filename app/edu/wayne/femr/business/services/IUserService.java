package edu.wayne.femr.business.services;

import edu.wayne.femr.data.models.User;

public interface IUserService {
    User createUser(String firstName, String lastName, String email, String password);
    User findByEmail(String email);
}
