package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.library.library.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByName(String category);

    List<Book> findAllByNameContainingIgnoreCase(String name);

    List<Book> findAllByNameContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrLibrary_NameContainingIgnoreCaseOrCategoriesContainsIgnoreCase(
            String name,String author,String libraryName,String categoryName
                                                                                                                                                  );
}
