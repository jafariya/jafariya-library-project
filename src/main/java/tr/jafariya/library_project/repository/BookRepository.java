package tr.jafariya.library_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.jafariya.library_project.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByName(String name);
}
