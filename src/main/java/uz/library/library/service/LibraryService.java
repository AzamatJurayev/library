package uz.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.LibraryDto;
import uz.library.library.entity.Book;
import uz.library.library.entity.Permission;
import uz.library.library.entity.Library;
import uz.library.library.repository.BookRepository;
import uz.library.library.repository.LibraryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    BookRepository bookRepository;

    public ApiResponse getAll() {
        return ApiResponse.builder().message("Mana").success(true).data(libraryRepository.findAll()).build();
    }

    public ApiResponse save(LibraryDto libraryDto) {
        Library library = new Library();
        library.setName(libraryDto.getName());
        List<Book> books = new ArrayList<>();
        for (Long aLong : libraryDto.getBookList()) {
            if (bookRepository.findById(aLong).isPresent()){
                books.add(bookRepository.findById(aLong).get());
            }
        }
        library.setBooks(books);
        Library save = libraryRepository.save(library);
        if (save != null) {
            return ApiResponse.builder().data(save).message("Saved!").success(true).build();
        } else {
            return ApiResponse.builder().data(save).message("Error!").success(false).build();
        }
    }

    public ApiResponse getOne(Long id) {
        Optional<Library> byId = libraryRepository.findById(id);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse getOneByName(String name) {
        Optional<Library> byId = libraryRepository.findByName(name);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse edit(Long id, LibraryDto libraryDto) {
        Optional<Library> byId = libraryRepository.findById(id);
        if (byId.isPresent()) {
            Library library = byId.get();
            library.setName(libraryDto.getName());
            List<Book> books = new ArrayList<>();
            for (Long aLong : libraryDto.getBookList()) {
                if (bookRepository.findById(aLong).isPresent()){
                    books.add(bookRepository.findById(aLong).get());
                }
            }
            library.setBooks(books);
            libraryRepository.save(library);
            return ApiResponse.builder().message("Edited!").success(true).data(library).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }

    public ApiResponse delete(Long id) {
        Optional<Library> byId = libraryRepository.findById(id);
        if (byId.isPresent()) {
            libraryRepository.deleteById(id);
            return ApiResponse.builder().message("Deleted!").success(true).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }
}
