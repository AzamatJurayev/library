package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import uz.library.library.entity.Rental;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental,Long> {
    List<Rental> findAllByUserAndDateOfUseAfterOrderByIdDesc(UserDetails userDetails,LocalDateTime currentDateTime);
    List<Rental> findAllByUserAndBook_NameContainingIgnoreCaseAndDateOfUseAfter(UserDetails userDetails,String name,LocalDateTime currentDateTime);
    List<Rental> findAllByUserAndDateOfUseAfterOrderByDateOfUse(UserDetails userDetails,LocalDateTime currentDateTime);
    List<Rental> findAllByUserAndDateOfUseBefore(UserDetails userDetails,LocalDateTime currentDateTime);

}
