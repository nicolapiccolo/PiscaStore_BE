package it.unito.order_service.repository;

import it.unito.order_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdUser(Long id);
}
