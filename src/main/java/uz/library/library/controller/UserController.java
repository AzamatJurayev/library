package uz.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.UserDto;
import uz.library.library.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @PreAuthorize("hasAnyAuthority('R_USER')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PreAuthorize("hasAnyAuthority('R_USER')")
    @GetMapping("/select/{name}")
    public ResponseEntity<?> selectByName(@PathVariable String name){
        ApiResponse response = userService.getAllByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize("hasAnyAuthority('R_USER')")
    @GetMapping("/select/{phone}")
    public ResponseEntity<?> selectByPhone(@PathVariable String phone){
        ApiResponse response = userService.selectByPhone(phone);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize("hasAnyAuthority('R_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = userService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize("hasAnyAuthority('R_USER')")
    @GetMapping("/{name}")
    public ResponseEntity<?> getOneByName(@PathVariable String name){
        ApiResponse response = userService.getOneByName(name);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAnyAuthority('U_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody UserDto user){
        ApiResponse response = userService.edit(id, user);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('D_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = userService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
