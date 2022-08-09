package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import uz.library.library.entity.Role;
import uz.library.library.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<UserDetails> findByPhone(String phone);
}
