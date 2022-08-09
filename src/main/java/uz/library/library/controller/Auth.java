package uz.library.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.LoginDto;
import uz.library.library.dto.RegisterDto;
import uz.library.library.security.JwtProvider;
import uz.library.library.service.UserService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Auth {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDTO) {
        String token = jwtProvider.generateToken(loginDTO.getPhone());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        ApiResponse response = userService.register(registerDto);
        String token = jwtProvider.generateToken(registerDto.getPhone());
        return ResponseEntity.ok(token);
    }
}
