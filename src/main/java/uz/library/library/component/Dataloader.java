package uz.library.library.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.library.library.config.SecuritySetting;
import uz.library.library.controller.CategoryController;
import uz.library.library.entity.*;
import uz.library.library.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Dataloader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final RentalRepository rentalRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            List<Permission> admin = new ArrayList<>();
            admin.add(Permission.R_BOOK);
            admin.add(Permission.R_CATEGORY);

            admin.add(Permission.U_USER);
            admin.add(Permission.R_RENTAL);
            admin.add(Permission.D_RENTAL);
            admin.add(Permission.U_RENTAL);

            admin.add(Permission.U_BOOK);
            admin.add(Permission.C_BOOK);
            admin.add(Permission.D_BOOK);

            admin.add(Permission.C_LIBRARY);
            admin.add(Permission.R_LIBRARY);
            admin.add(Permission.U_LIBRARY);
            admin.add(Permission.D_LIBRARY);

            admin.add(Permission.C_ROLE);
            admin.add(Permission.R_ROLE);
            admin.add(Permission.U_ROLE);
            admin.add(Permission.D_ROLE);

            admin.add(Permission.R_USER);

            List<Permission> user = new ArrayList<>();
            user.add(Permission.R_BOOK);
            user.add(Permission.R_CATEGORY);
            user.add(Permission.R_LIBRARY);
            user.add(Permission.U_USER);
            user.add(Permission.D_USER);
            user.add(Permission.C_RENTAL);
            user.add(Permission.R_RENTAL);
            user.add(Permission.D_RENTAL);
            user.add(Permission.U_RENTAL);
            Role admin1 = roleRepository.save(new Role(1l, "ADMIN", admin));
            Role user1 = roleRepository.save(new Role(2l, "USER", user));

            libraryRepository.save(new Library(1l,"Book City"));
            libraryRepository.save(new Library(2l,"Books shop"));

            Category matematika = categoryRepository.save(new Category(1l, "Matematika"));
            Category tarix = categoryRepository.save(new Category(2l, "Tarix"));

            List<Category> categories = new ArrayList<>();
            List<Category> categories1 = new ArrayList<>();

            categories.add(matematika);
            categories1.add(tarix);

            Book qambaraloyev = bookRepository.save(new Book(1l, "Matem 7", "Qambaraloyev", libraryRepository.findById(1l).get(), categories));
            Book madaminov = bookRepository.save(new Book(2l, "Tarix 7", "Madaminov", libraryRepository.findById(2l).get(), categories1));

            userRepository.save(new User(1l, "Big Admin", passwordEncoder.encode("123"), "111", admin1));
            User azamat_jurayev = userRepository.save(new User(2l, "Azamat Jurayev", passwordEncoder.encode("111"), "777", user1));

            rentalRepository.save(new Rental(1l,qambaraloyev,azamat_jurayev, LocalDateTime.now().plusDays(3)));
            rentalRepository.save(new Rental(2l,madaminov,azamat_jurayev, LocalDateTime.now()));
        }
    }
}
