package uz.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.library.library.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental,Long> {
}
