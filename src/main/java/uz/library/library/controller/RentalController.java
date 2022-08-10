package uz.library.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.RentalDto;
import uz.library.library.dto.RentalDto;
import uz.library.library.repository.RentalRepository;
import uz.library.library.repository.UserRepository;
import uz.library.library.security.JwtProvider;
import uz.library.library.service.RentalService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @PreAuthorize("hasAuthority('R_RENTAL')")
    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        String usernameFromToken = jwtProvider.getUsernameFromToken(jwtProvider.getToken(request));
        ApiResponse response = rentalService.getAll(usernameFromToken);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('R_RENTAL')")
    @GetMapping("/used")
    public ResponseEntity<?> getAllUsed(HttpServletRequest request) {
        String usernameFromToken = jwtProvider.getUsernameFromToken(jwtProvider.getToken(request));
        ApiResponse response = rentalService.getAllUsed(usernameFromToken);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('R_RENTAL')")
    @GetMapping("/")
    public ResponseEntity<?> search(HttpServletRequest request,@RequestParam(name = "name") String bookName) {
        String usernameFromToken = jwtProvider.getUsernameFromToken(jwtProvider.getToken(request));
        ApiResponse response = rentalService.search(usernameFromToken,bookName);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('R_RENTAL')")
    @GetMapping("/order")
    public ResponseEntity<?> getAllByOrderId(HttpServletRequest request) {
        String usernameFromToken = jwtProvider.getUsernameFromToken(jwtProvider.getToken(request));
        ApiResponse response = rentalService.getAllOrderById(usernameFromToken);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('C_RENTAL')")
    @PostMapping
    public ResponseEntity<?> rentBook(HttpServletRequest request,@RequestBody RentalDto rentalDto){
        String usernameFromToken = jwtProvider.getUsernameFromToken(jwtProvider.getToken(request));
        ApiResponse response = rentalService.rentBook(usernameFromToken,rentalDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('R_RENTAL')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse response = rentalService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
    @PreAuthorize("hasAuthority('U_RENTAL')")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody RentalDto rental){
        ApiResponse response = rentalService.edit(id, rental);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
    @PreAuthorize("hasAuthority('D_RENTAL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse response = rentalService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
