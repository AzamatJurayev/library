package uz.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.entity.Book;
import uz.library.library.service.BookService;


@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    BookService bookService;
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(bookService.getAll());
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book){
        ApiResponse save = bookService.save(book);
        return ResponseEntity.status(save.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(save);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = bookService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getOne(@PathVariable String name){
        ApiResponse response = bookService.getOneByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody Book book){
        ApiResponse response = bookService.edit(id, book);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = bookService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
