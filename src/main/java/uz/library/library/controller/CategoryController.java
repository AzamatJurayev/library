package uz.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.entity.Category;
import uz.library.library.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @PreAuthorize("hasAnyAuthority('R_CATEGORY')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }
    @PreAuthorize("hasAuthority('C_CATEGORY')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Category category){
        ApiResponse save = categoryService.save(category);
        return ResponseEntity.status(save.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(save);
    }
    @PreAuthorize("hasAnyAuthority('R_CATEGORY')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = categoryService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_CATEGORY')")
    @GetMapping("/{name}")
    public ResponseEntity<?> getOneByName(@PathVariable String name){
        ApiResponse response = categoryService.getOneByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('R_CATEGORY')")
    @GetMapping("/search/{name}")
    public ResponseEntity<?> select(@PathVariable String name){
        ApiResponse response = categoryService.getAllByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('U_CATEGORY')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody Category category){
        ApiResponse response = categoryService.edit(id, category);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('D_CATEGORY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = categoryService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
