package tr.jafariya.library_project.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor //will create constructor with only final fields

public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRep;



    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRep.findById(id).orElseThrow();
        return convertEntityToDto(author);
    }


/*
    public AuthorDto getByNameV2(String name) {
        Author author = authorRep.findAuthorByNameBySql(name)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return convertEntityToDto(author);
    } */

    @Override
    public AuthorDto getByNameV2(String name) {
        Author author = authorRep.findAuthorByNameBySql(name).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
         Author a = authorRep.findAuthorByName(name).orElseThrow();
        return convertEntityToDto(a);
    }

    @Override
    public AuthorDto getAuthorByNameV3(String name) {
        Specification<Author> authorSpecification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
        });
        Author a = authorRep.findOne((authorSpecification)).orElseThrow();
        return convertEntityToDto(a);
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = authorRep.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        Author author = authorRep.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRep.save(author);
        AuthorDto authorDto = convertEntityToDto(savedAuthor);
        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRep.deleteById(id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRep.findAll();
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
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
