package it.unito.order_service.repository;

import it.unito.order_service.entity.Bag;
import it.unito.order_service.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByIdProduct(Long id);
    Optional<List<Item>> findByBag(Bag bag);
}
