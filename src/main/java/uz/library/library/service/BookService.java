package uz.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.BookDto;
import uz.library.library.entity.Book;
import uz.library.library.entity.Library;
import uz.library.library.repository.BookRepository;
import uz.library.library.repository.LibraryRepository;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    LibraryRepository libraryRepository;

    public ApiResponse getAll() {
        return ApiResponse.builder().message("Mana").success(true).data(bookRepository.findAll()).build();
    }

    public ApiResponse save(BookDto bookDto) {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        Optional<Library> byId = libraryRepository.findById(bookDto.getLibraryId());
        if (byId.isPresent()) {
            book.setLibrary(byId.get());
        }else return ApiResponse.builder().data(null).message("Error!").success(false).build();
        Book save = bookRepository.save(book);
        if (save != null) {
            return ApiResponse.builder().data(save).message("Saved!").success(true).build();
        } else {
            return ApiResponse.builder().data(save).message("Error!").success(false).build();
        }
    }

    public ApiResponse getOne(Long id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }
    public ApiResponse getOneByName(String name) {
        Optional<Book> byId = bookRepository.findByName(name);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse edit(Long id,BookDto bookDto) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()){
            Book book = byId.get();
            book.setAuthor(bookDto.getAuthor());
            Optional<Library> libraryId = libraryRepository.findById(bookDto.getLibraryId());
            if (libraryId.isPresent()) {
                book.setLibrary(libraryId.get());
            }else return ApiResponse.builder().data(null).message("Error!").success(false).build();

            return ApiResponse.builder().message("Edited!").success(true).data(book).build();
        }
        else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }

    public ApiResponse delete(Long id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()){
            bookRepository.deleteById(id);
            return ApiResponse.builder().message("Deleted!").success(true).build();
        }
        else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }
}
