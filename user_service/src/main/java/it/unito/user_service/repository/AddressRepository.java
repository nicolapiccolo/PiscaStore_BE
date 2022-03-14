package it.unito.user_service.repository;

import it.unito.user_service.entity.Address;
import it.unito.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);
    void deleteById(Long id);
}
