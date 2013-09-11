package edu.wayne.femr.business.services;

import edu.wayne.femr.data.models.User;

public interface ISessionService {
    User createSession(String email, String password);
}
