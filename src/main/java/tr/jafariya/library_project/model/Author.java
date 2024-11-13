package tr.jafariya.library_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Entity //class will be connected to a table in Data Base

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //shows how will be initialized in table
    public Long id;

    @Column(nullable = false) //no possible being empty
    private String name;

    @Column(nullable = false)
    private String surname;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

}
