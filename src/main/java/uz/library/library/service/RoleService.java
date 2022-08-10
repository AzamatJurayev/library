package uz.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.RoleDto;
import uz.library.library.entity.Permission;
import uz.library.library.entity.Role;
import uz.library.library.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ApiResponse getAll() {
        return ApiResponse.builder().message("Mana").success(true).data(roleRepository.findAll()).build();
    }

    public ApiResponse save(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        List<Permission> permissions = new ArrayList<>();
        for (String permission : roleDto.getPermissions()) {
            permissions.add(Permission.valueOf(permission));
        }
        role.setPermission(permissions);
        Role save = roleRepository.save(role);
        if (save != null) {
            return ApiResponse.builder().data(save).message("Saved!").success(true).build();
        } else {
            return ApiResponse.builder().data(save).message("Error!").success(false).build();
        }
    }

    public ApiResponse getOne(Long id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse getOneByName(String name) {
        Optional<Role> byId = roleRepository.findByName(name);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse getAllByName(String name) {
        List<Role> byName = roleRepository.findAllByNameContainingIgnoreCase(name);
        return ApiResponse.builder().data(byName).success(true).message("Mana").build();

    }

    public ApiResponse edit(Long id, RoleDto roleDto) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()) {
            Role role = byId.get();
            role.setName(roleDto.getName());
            List<Permission> permissions = new ArrayList<>();
            for (String permission : roleDto.getPermissions()) {
                permissions.add(Permission.valueOf(permission));
            }
            role.setPermission(permissions);
            roleRepository.save(role);
            return ApiResponse.builder().message("Edited!").success(true).data(role).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }

    public ApiResponse delete(Long id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()) {
            roleRepository.deleteById(id);
            return ApiResponse.builder().message("Deleted!").success(true).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }
}
