package tr.jafariya.library_project.service;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto getGenreById(Long id);
    List<GenreDto> getAllGenres();
}
