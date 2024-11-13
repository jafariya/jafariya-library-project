package tr.jafariya.library_project.service;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tr.jafariya.library_project.model.Author;
import tr.jafariya.library_project.model.Book;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.repository.AuthorRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        String name = "Fyodor";
        String surname = "Dostoyevski";
        Set<Book> books = new HashSet<>();

        Author a = new Author(id, name, surname, books);

        //Now we need to descrive behavior of our mock object
        when(authorRepository.findById(id)).thenReturn(Optional.of(a));

        AuthorDto authorDto = authorService.getAuthorById(id);
        verify(authorRepository).findById(id);
        //checks are objects same, here we want to check if DTO is same with author from Rep
        assertEquals(authorDto.getId(), a.getId());
        assertEquals(authorDto.getName(), a.getName());
        assertEquals(authorDto.getSurname(), a.getSurname());
    }

    @Test
    public void testGetAuthorByIdFailed() {
        Long id = 1L;

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByNameV1() {
        String name = "Fyodor";
        String surname = "Dostoyevski";
        Long id = 1L;
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV1(name);

        verify(authorRepository).findAuthorByName(name);

        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV1Failed() {
        String name = "Abrakadabra";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> authorService.getAuthorByNameV1(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testGetAuthorByNameV2() {

        String name = "Fyodor";
        String surname = "Dostoyevski";
        Long id = 1L;
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getByNameV2(name);

        verify(authorRepository).findAuthorByNameBySql(name);

        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV2Failed() {
        String name = "Abrakadabra";

        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> authorService.getByNameV2(name));
        verify(authorRepository).findAuthorByNameBySql(name);
    }




}
