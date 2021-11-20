package it.unito.pisca.repository;

import it.unito.pisca.entity.Address;
import it.unito.pisca.entity.Role;
import it.unito.pisca.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);
}
