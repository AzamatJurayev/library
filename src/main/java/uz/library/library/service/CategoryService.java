package uz.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.entity.Category;
import uz.library.library.entity.Role;
import uz.library.library.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse getAll() {
        return ApiResponse.builder().message("Mana").success(true).data(categoryRepository.findAll()).build();
    }

    public ApiResponse save(Category category) {
        Category save = categoryRepository.save(category);
        if (save != null) {
            return ApiResponse.builder().data(save).message("Saved!").success(true).build();
        } else {
            return ApiResponse.builder().data(save).message("Error!").success(false).build();
        }
    }
    public ApiResponse getAllByName(String name) {
        List<Category> byName = categoryRepository.findAllByNameContainingIgnoreCase(name);
        return ApiResponse.builder().data(byName).success(true).message("Mana").build();

    }
    public ApiResponse getOne(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }
    public ApiResponse getOneByName(String name) {
        Optional<Category> byId = categoryRepository.findByName(name);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }

    public ApiResponse edit(Long id,Category category) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()){
            Category category1 = byId.get();
            category1.setName(category.getName());
            categoryRepository.save(category1);
            return ApiResponse.builder().message("Edited!").success(true).data(category1).build();
        }
        else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }

    public ApiResponse delete(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()){
            categoryRepository.deleteById(id);
            return ApiResponse.builder().message("Deleted!").success(true).build();
        }
        else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }
}
