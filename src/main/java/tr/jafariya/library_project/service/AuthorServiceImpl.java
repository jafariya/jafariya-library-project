package tr.jafariya.library_project.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr.jafariya.library_project.dto.AuthorCreateDto;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.AuthorUpdateDto;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.model.Author;
import tr.jafariya.library_project.model.Book;
import tr.jafariya.library_project.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
//will create constructor with only final fields

public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRep;



    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRep.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new IllegalStateException("No author present");
        }
    }



    @Override
    public AuthorDto getByNameV2(String name) {
        log.info("Try to find author by name (V2) {}", name);
        Author author = authorRep.findAuthorByNameBySql(name)
                .orElseThrow(() -> {
                    log.error("Author with name {} not found (V2)", name);
                    return new RuntimeException("Author not found");
                });
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author found (V2): {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        log.info("Try to find author by name (V1) {}", name);
        Author author = authorRep.findAuthorByName(name).orElseThrow(() -> {
            log.error("Author with name {} not found (V1)", name);
            return new RuntimeException("Author not found");
        });
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author found (V1): {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorBySurname(String surname) {
        log.info("Try to find author by surname (V3) {}", surname);
        Specification<Author> authorSpecification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("surname"), surname);
            }
        });
        Author author = authorRep.findOne(authorSpecification).orElseThrow(() -> {
            log.error("Author with surname {} not found (V3)", surname);
            return new RuntimeException("Author not found");
        });
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author found (V3): {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Creating author with data {}", authorCreateDto.toString());
        Author author = authorRep.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author created: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Updating author with data {}", authorUpdateDto.toString());
        Author author = authorRep.findById(authorUpdateDto.getId()).orElseThrow(() -> {
            log.error("Author with id {} not found for update", authorUpdateDto.getId());
            return new RuntimeException("Author not found");
        });
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRep.save(author);
        AuthorDto authorDto = convertEntityToDto(savedAuthor);
        log.info("Author updated: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Deleting author with id {}", id);
        authorRep.deleteById(id);
        log.info("Author with id {} deleted", id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Fetching all authors");
        List<Author> authors = authorRep.findAll();
        List<AuthorDto> authorDtoList = authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        log.info("Total authors found: {}", authorDtoList.size());
        return authorDtoList;
    }
    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }


    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = author.getBooks().stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();


        if (bookDtoList.isEmpty()) {
            bookDtoList = null;
        }


        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }
}
