package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.library.library.entity.Library;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library,Long> {
    Optional<Library> findByName(String category);

    List<Library> findAllByNameContainingIgnoreCase(String name);

}
