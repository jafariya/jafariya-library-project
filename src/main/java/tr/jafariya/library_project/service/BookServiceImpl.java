package tr.jafariya.library_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.jafariya.library_project.dto.*;
import tr.jafariya.library_project.model.Author;
import tr.jafariya.library_project.model.Genre;
import tr.jafariya.library_project.repository.AuthorRepository;
import tr.jafariya.library_project.repository.BookRepository;
import tr.jafariya.library_project.model.Book;
import tr.jafariya.library_project.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRep;

    @Override
    public BookDto getBookByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Author author = authorRepository.findById(bookCreateDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Genre genre = genreRep.findByName(bookCreateDto.getGenreName())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        Book book = convertDtoToEntity(bookCreateDto, genre);
        book.setAuthors(Set.of(author));


        Book savedBook = bookRepository.save(book);

        return convertEntityToDto(savedBook);
    }
    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        Genre genre = genreRep.findByName(bookUpdateDto.getGenreName())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        book.setGenre(genre);

        Set<Author> authors = new HashSet<>();
        for (Long authorId : bookUpdateDto.getAuthorIds()) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            authors.add(author);
        }
        book.setAuthors(authors);

        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto, Genre genre) {
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();
    }

    private BookDto convertEntityToDto(Book book) {

        List<AuthorDto> authorDtoList = null;
        if (book.getAuthors() != null) {
            authorDtoList = book.getAuthors()
                    .stream()
                    .map(author -> AuthorDto.builder()
                            .id(author.getId())
                            .name(author.getName())
                            .surname(author.getSurname())
                            .build())
                    .toList();
        }

        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
    }

}
