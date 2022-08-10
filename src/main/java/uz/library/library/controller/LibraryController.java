package uz.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.LibraryDto;
import uz.library.library.service.LibraryService;


@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    LibraryService libraryService;
    @PreAuthorize("hasAnyAuthority('R_LIBRARY')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(libraryService.getAll());
    }
    @PreAuthorize("hasAuthority('C_LIBRARY')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody LibraryDto library){
        ApiResponse save = libraryService.save(library);
        return ResponseEntity.status(save.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(save);
    }
    @PreAuthorize("hasAnyAuthority('R_LIBRARY')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = libraryService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_LIBRARY')")
    @GetMapping("/{name}")
    public ResponseEntity<?> getOneByName(@PathVariable String name){
        ApiResponse response = libraryService.getOneByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_LIBRARY')")
    @GetMapping("/search/{name}")
    public ResponseEntity<?> select(@PathVariable String name){
        ApiResponse response = libraryService.getAllByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('U_LIBRARY')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody LibraryDto library){
        ApiResponse response = libraryService.edit(id, library);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('D_LIBRARY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = libraryService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
