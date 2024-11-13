package tr.jafariya.library_project.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.jafariya.library_project.dto.*;
import tr.jafariya.library_project.model.Author;
import tr.jafariya.library_project.model.Book;
import tr.jafariya.library_project.model.Genre;
import tr.jafariya.library_project.repository.AuthorRepository;
import tr.jafariya.library_project.repository.BookRepository;
import tr.jafariya.library_project.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRep;

    @Override
    public BookDto getBookByNameV1(String name) {
        log.info("Trying to find book by name {}", name);
        Book book = bookRepository.findBookByName(name).orElseThrow(() -> {
            log.error("Book with name {} not found", name);
            return new RuntimeException("Book not found");
        });
        BookDto bookDto = convertEntityToDto(book);
        log.info("Found book: {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Creating book with name: {}", bookCreateDto.getName());

        Author author = authorRepository.findById(bookCreateDto.getAuthorId()).orElseThrow(() -> {
            log.error("Author with ID {} not found", bookCreateDto.getAuthorId());
            return new RuntimeException("Author not found");
        });

        Genre genre = genreRep.findByName(bookCreateDto.getGenreName()).orElseThrow(() -> {
            log.error("Genre with name {} not found", bookCreateDto.getGenreName());
            return new RuntimeException("Genre not found");
        });

        Book book = convertDtoToEntity(bookCreateDto, genre);
        book.setAuthors(Set.of(author));
        Book savedBook = bookRepository.save(book);

        BookDto bookDto = convertEntityToDto(savedBook);
        log.info("Book created : {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Updating book with ID: {}", bookUpdateDto.getId());

        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow(() -> {
            log.error("Book with ID {} not found", bookUpdateDto.getId());
            return new RuntimeException("Book not found");
        });

        book.setName(bookUpdateDto.getName());
        Genre genre = genreRep.findByName(bookUpdateDto.getGenreName()).orElseThrow(() -> {
            log.error("Genre with name {} not found", bookUpdateDto.getGenreName());
            return new RuntimeException("Genre not found");
        });
        book.setGenre(genre);

        Set<Author> authors = new HashSet<>();
        for (Long authorId : bookUpdateDto.getAuthorIds()) {
            Author author = authorRepository.findById(authorId).orElseThrow(() -> {
                log.error("Author with ID {} not found", authorId);
                return new RuntimeException("Author not found");
            });
            authors.add(author);
        }
        book.setAuthors(authors);

        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        log.info("Book updated : {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Getting all books");
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        log.info("Total books found: {}", bookDtos.size());
        return bookDtos;
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
        log.info("Book with ID {} deleted", id);
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto, Genre genre) {
        log.info("Converting Dto to Book entity for name: {}", bookCreateDto.getName());
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();
    }

    private BookDto convertEntityToDto(Book book) {
        log.info("Converting Book  to Dto for book ID: {}", book.getId());
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

        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre() != null ? book.getGenre().getName() : null)
                .authors(authorDtoList)
                .build();
        log.info("Converted BookDto: {}", bookDto.toString());
        return bookDto;
    }
}
