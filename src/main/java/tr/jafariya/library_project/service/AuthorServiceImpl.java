package tr.jafariya.library_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.model.Author;
import tr.jafariya.library_project.repository.AuthorRepository;

import java.util.List;

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

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();

        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }
}
