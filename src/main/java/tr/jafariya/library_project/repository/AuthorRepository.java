package tr.jafariya.library_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.jafariya.library_project.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {


}
