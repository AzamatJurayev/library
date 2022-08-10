package uz.library.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.library.library.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {



    Optional<Category> findByName(String category);

    List<Category> findAllByNameContainingIgnoreCase(String name);
}
