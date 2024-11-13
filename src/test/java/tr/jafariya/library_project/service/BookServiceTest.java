package tr.jafariya.library_project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.model.Author;
import tr.jafariya.library_project.model.Book;
import tr.jafariya.library_project.model.Genre;
import tr.jafariya.library_project.repository.AuthorRepository;
import tr.jafariya.library_project.repository.BookRepository;
import tr.jafariya.library_project.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRep;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void getBookByNameV1Test() {

        Genre genre = new Genre();
        genre.setName("Dystopian");

        Author author = new Author();
        author.setId(1L);
        author.setName("George");
        author.setSurname("Orwell");

        Book book = new Book();
        book.setId(1L);
        book.setName("1984");
        book.setGenre(genre);
        book.setAuthors(Set.of(author));

        BookDto expectedBookDto = BookDto.builder()
                .id(1L)
                .name("1984")
                .genre("Dystopian")
                .authors(List.of(new AuthorDto(1L, "George", "Orwell", null)))
                .build();


        when(bookRepository.findBookByName("1984")).thenReturn(Optional.of(book));


        BookDto actualBookDto = bookService.getBookByNameV1("1984");


        assertEquals(expectedBookDto, actualBookDto);
        verify(bookRepository, times(1)).findBookByName("1984");
    }

    @Test
    public void getBookByNameV1TestFailed() {

        when(bookRepository.findBookByName("1984")).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookByNameV1("1984");
        });

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findBookByName("1984");
    }
}
