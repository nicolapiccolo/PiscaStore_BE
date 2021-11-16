package it.unito.pisca.repository;

import it.unito.pisca.entity.Role;
import it.unito.pisca.enums.RoleNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleNumber name);
}
