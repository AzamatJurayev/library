package uz.library.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Library(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "library")
    @JsonIgnore
    private List<Book> books;

}
