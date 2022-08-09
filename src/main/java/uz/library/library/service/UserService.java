package uz.library.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.RegisterDto;
import uz.library.library.entity.User;
import uz.library.library.repository.UserRepository;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public ApiResponse register(RegisterDto registerDto) {
        User user = new User();
        user.setPhone(registerDto.getPhone());
        user.setFullName(registerDto.getFullName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return ApiResponse.builder().data(user).message("register amalga oshirildi").success(true).build();
    }
}
