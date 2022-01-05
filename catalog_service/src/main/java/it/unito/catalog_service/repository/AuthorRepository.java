package it.unito.catalog_service.repository;

import it.unito.catalog_service.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Optional<Author> findByIdUser(Long id);
    Optional<Author> findById(Long id);
}
