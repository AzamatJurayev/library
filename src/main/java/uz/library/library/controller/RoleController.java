package uz.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.RoleDto;
import uz.library.library.service.RoleService;


@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(roleService.getAll());
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDto role){
        ApiResponse save = roleService.save(role);
        return ResponseEntity.status(save.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(save);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = roleService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @GetMapping("/{name}")
    public ResponseEntity<?> getOneByName(@PathVariable String name){
        ApiResponse response = roleService.getOneByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody RoleDto role){
        ApiResponse response = roleService.edit(id, role);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = roleService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
