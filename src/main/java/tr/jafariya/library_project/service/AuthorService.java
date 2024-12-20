package tr.jafariya.library_project.service;

import tr.jafariya.library_project.dto.AuthorCreateDto;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.AuthorUpdateDto;
import tr.jafariya.library_project.dto.BookDto;

import java.util.List;

public interface AuthorService {
    AuthorDto getByNameV2(String name);
    AuthorDto getAuthorById(Long id);
    AuthorDto getAuthorByNameV1(String name);
    AuthorDto getAuthorBySurname(String name);
    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);
    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);
    void deleteAuthor(Long id);
    List<AuthorDto> getAllAuthors();
}
