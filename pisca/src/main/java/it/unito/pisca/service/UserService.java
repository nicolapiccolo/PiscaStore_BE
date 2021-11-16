package it.unito.pisca.service;

import it.unito.pisca.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String email);
    boolean existsEmail(String email);
    User save(User user);
}
