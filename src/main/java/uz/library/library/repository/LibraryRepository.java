package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.library.library.entity.Library;
import uz.library.library.entity.Role;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library,Long> {
    Optional<Library> findByName(String category);

    List<Role> findAllByNameContainingIgnoreCase(String name);
}
