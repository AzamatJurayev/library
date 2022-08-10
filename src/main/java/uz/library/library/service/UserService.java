package uz.library.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.UserDto;
import uz.library.library.dto.RegisterDto;
import uz.library.library.entity.User;
import uz.library.library.entity.Library;
import uz.library.library.entity.User;
import uz.library.library.repository.RoleRepository;
import uz.library.library.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public ApiResponse register(RegisterDto registerDto) {
        User user = new User();
        user.setPhone(registerDto.getPhone());
        user.setFullName(registerDto.getFullName());
        System.out.println(roleRepository.findById(registerDto.getRoleId()));
        if (roleRepository.findById(registerDto.getRoleId()).isPresent()) {
            user.setRole(roleRepository.findById(registerDto.getRoleId()).get());
        }
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return ApiResponse.builder().data(user).message("register amalga oshirildi").success(true).build();
    }

    public ApiResponse getAll() {
        return ApiResponse.builder().message("Mana").success(true).data(userRepository.findAll()).build();
    }

    public ApiResponse getOne(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse getOneByName(String name) {
        Optional<User> byId = userRepository.findByFullName(name);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse getAllByName(String name) {
        List<User> byName = userRepository.findAllByFullNameContainingIgnoreCase(name);
        return ApiResponse.builder().data(byName).success(true).message("Mana").build();

    }
    public ApiResponse selectByPhone(String phone) {
        User byName = userRepository.findByPhoneContainingIgnoreCase(phone);
        return ApiResponse.builder().data(byName).success(true).message("Mana").build();
    }

    public ApiResponse edit(Long id, UserDto userDto) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User userUpdate = byId.get();
            userUpdate.setFullName(userDto.getFullName());
            userUpdate.setPassword(userDto.getPassword());
            if (roleRepository.findById(userDto.getRoleId()).isPresent()) {
                userUpdate.setRole(roleRepository.findById(userDto.getRoleId()).get());
            } else return ApiResponse.builder().data(null).message("Error!").success(false).build();
            userRepository.save(userUpdate);
            return ApiResponse.builder().message("Edited!").success(true).data(userUpdate).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }

    public ApiResponse delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.deleteById(id);
            return ApiResponse.builder().message("Deleted!").success(true).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }
}
