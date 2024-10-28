package tr.jafariya.library_project.service;

import lombok.RequiredArgsConstructor;
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
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRep;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRep.findById(id)
                .orElseThrow(() -> new RuntimeException(" " + id));
        return convertEntityToDto(genre);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        List<Genre> genres = genreRep.findAll();
        return genres.stream().map(this::convertEntityToDto).collect(Collectors.toList());
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
