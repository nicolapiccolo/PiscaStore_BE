package it.unito.user_service.service;

import it.unito.user_service.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String email);
    boolean existsEmail(String email);
    User save(User user);
}
