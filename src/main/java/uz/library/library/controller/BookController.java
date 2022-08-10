package uz.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.BookDto;
import uz.library.library.entity.Book;
import uz.library.library.service.BookService;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;
    @PreAuthorize("hasAnyAuthority('R_BOOK')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(bookService.getAll());
    }
    @PreAuthorize("hasAuthority('C_BOOK')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookDto book){
        ApiResponse save = bookService.save(book);
        return ResponseEntity.status(save.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(save);
    }
    @PreAuthorize("hasAnyAuthority('R_BOOK')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = bookService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_BOOK')")
    @GetMapping("getByName/{name}")
    public ResponseEntity<?> getOne(@PathVariable String name){
        ApiResponse response = bookService.getOneByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_BOOK')")
    @GetMapping("/search/{name}")
    public ResponseEntity<?> select(@PathVariable String name){
        ApiResponse response = bookService.getAllByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_BOOK')")
    @GetMapping("/searcher")
    public ResponseEntity<?> search(
            @RequestParam(name = "name",defaultValue = "") String name,
            @RequestParam(name = "author",defaultValue = "") String author,
            @RequestParam(name = "libName",defaultValue = "") String libraryName,
            @RequestParam(name = "catName",defaultValue = "") String categoryName){
        ApiResponse response = bookService.search(name,author,libraryName,categoryName);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('U_BOOK')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody BookDto book){
        ApiResponse response = bookService.edit(id, book);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('D_BOOK')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = bookService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
