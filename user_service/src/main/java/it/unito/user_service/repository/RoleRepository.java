package it.unito.user_service.repository;

import it.unito.user_service.entity.Role;
import it.unito.user_service.enums.RoleNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleNumber name);
}
