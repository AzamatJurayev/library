package uz.library.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.library.library.dto.ApiResponse;
import uz.library.library.dto.RentalDto;
import uz.library.library.entity.Permission;
import uz.library.library.entity.Rental;
import uz.library.library.entity.Rental;
import uz.library.library.entity.User;
import uz.library.library.repository.BookRepository;
import uz.library.library.repository.RentalRepository;
import uz.library.library.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    public ApiResponse getAll(String usernameFromToken) {
        UserDetails userDetails = userRepository.findByPhone(usernameFromToken).get();

        List<Rental> allByUserAndDateOfUseAfter = rentalRepository.findAllByUserAndDateOfUseAfterOrderByDateOfUse(userDetails, LocalDateTime.now());

        return ApiResponse.builder().data(allByUserAndDateOfUseAfter).message("Olingan Kitoblar").success(true).build();

    }
    public ApiResponse search(String usernameFromToken,String bookName) {
        UserDetails userDetails = userRepository.findByPhone(usernameFromToken).get();

        List<Rental> allByUserAndDateOfUseAfter = rentalRepository.findAllByUserAndBook_NameContainingIgnoreCaseAndDateOfUseAfter(userDetails,bookName, LocalDateTime.now());

        return ApiResponse.builder().data(allByUserAndDateOfUseAfter).message("Olingan Kitoblar").success(true).build();

    }
    public ApiResponse getAllOrderById(String usernameFromToken) {
        UserDetails userDetails = userRepository.findByPhone(usernameFromToken).get();

        List<Rental> allByUserAndDateOfUseAfter = rentalRepository.findAllByUserAndDateOfUseAfterOrderByIdDesc(userDetails, LocalDateTime.now());

        return ApiResponse.builder().data(allByUserAndDateOfUseAfter).message("Olingan Kitoblar").success(true).build();

    }
    public ApiResponse getAllUsed(String usernameFromToken) {
        UserDetails userDetails = userRepository.findByPhone(usernameFromToken).get();

        List<Rental> allByUserAndDateOfUseAfter = rentalRepository.findAllByUserAndDateOfUseBefore(userDetails, LocalDateTime.now());

        return ApiResponse.builder().data(allByUserAndDateOfUseAfter).message("Ishlatilingan Kitoblar").success(true).build();

    }
    public ApiResponse rentBook(String usernameFromToken, RentalDto rentalDto) {
        User userDetails = (User) userRepository.findByPhone(usernameFromToken).get();
        Rental rental = new Rental();
        rental.setUser(userDetails);
        if (bookRepository.findById(rentalDto.getBookId()).isPresent()) {
            rental.setBook(bookRepository.findById(rentalDto.getBookId()).get());
        }else ApiResponse.builder().success(false).message("ERROR").data("Not Found book!!!").build();
        rental.setDateOfUse(LocalDateTime.now().plusDays(rentalDto.getDateOfUse()));
        Rental save = rentalRepository.save(rental);
        return ApiResponse.builder().success(true).message("Rented!!!").data(save).build();
    }
    public ApiResponse getOne(Long id) {
        Optional<Rental> byId = rentalRepository.findById(id);
        if (byId.isPresent()) {
            return ApiResponse.builder().data(byId.get()).success(true).message("Mana").build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found").build();
        }
    }
    public ApiResponse edit(Long id, RentalDto rentalDto) {
        Optional<Rental> byId = rentalRepository.findById(id);
        if (byId.isPresent()) {
            Rental rental = byId.get();
            if (bookRepository.findById(rentalDto.getBookId()).isPresent()) {
                rental.setBook(bookRepository.findById(rentalDto.getBookId()).get());
            }else ApiResponse.builder().success(false).message("ERROR").data("Not Found book!!!").build();
            rental.setDateOfUse(LocalDateTime.now().plusDays(rentalDto.getDateOfUse()));
            rentalRepository.save(rental);
            return ApiResponse.builder().message("Edited!").success(true).data(rental).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }

    public ApiResponse delete(Long id) {
        Optional<Rental> byId = rentalRepository.findById(id);
        if (byId.isPresent()) {
            rentalRepository.deleteById(id);
            return ApiResponse.builder().message("Deleted!").success(true).build();
        } else {
            return ApiResponse.builder().success(false).message("Not Found!").build();
        }
    }
}
