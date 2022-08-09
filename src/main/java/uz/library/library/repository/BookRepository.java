package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.library.library.entity.Book;
import uz.library.library.entity.Role;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByName(String category);

    List<Role> findAllByNameContainingIgnoreCase(String name);
}
