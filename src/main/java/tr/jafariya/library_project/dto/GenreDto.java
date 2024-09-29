package tr.jafariya.library_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.jafariya.library_project.model.Book;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenreDto {
    private Long id;
    private String genre;
    private List<BookDto> books;

}