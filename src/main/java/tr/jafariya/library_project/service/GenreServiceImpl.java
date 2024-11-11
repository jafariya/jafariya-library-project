package tr.jafariya.library_project.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.dto.GenreDto;
import tr.jafariya.library_project.model.Book;
import tr.jafariya.library_project.model.Genre;
import tr.jafariya.library_project.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRep;

    @Override
    public GenreDto getGenreById(Long id) {
        log.info("Getting genre with ID: {}", id);
        Genre genre = genreRep.findById(id)
                .orElseThrow(() -> {
                    log.error("Genre with ID {} not found", id);
                    return new RuntimeException("Genre with ID " + id + " not found");
                });
        return convertEntityToDto(genre);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        log.info("Getting all genres");
        List<Genre> genres = genreRep.findAll();
        List<GenreDto> genreDtos = genres.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        log.info("Total genres found: {}", genreDtos.size());
        return genreDtos;
    }

    private GenreDto convertEntityToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> {
                    List<AuthorDto> authors = book.getAuthors().stream()
                            .map(author -> AuthorDto.builder()
                                    .id(author.getId())
                                    .name(author.getName())
                                    .surname(author.getSurname())
                                    .build())
                            .collect(Collectors.toList());

                    if (authors.isEmpty()) {
                        return null;
                    }

                    return BookDto.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .authors(authors)
                            .build();
                })
                .filter(bookDto -> bookDto != null)
                .distinct()
                .collect(Collectors.toList());

        return GenreDto.builder()
                .id(genre.getId())
                .genre(genre.getName())
                .books(bookDtoList)
                .build();
    }
}
