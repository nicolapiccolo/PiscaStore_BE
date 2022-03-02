package it.unito.order_service.repository;

import it.unito.order_service.entity.Bag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Bag, Long> {

    Optional<Bag> findById(Long id);
    Optional<List<Bag>> findByIdUser(Long id_user);
}
