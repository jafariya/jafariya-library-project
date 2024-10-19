package tr.jafariya.library_project.service;

import tr.jafariya.library_project.dto.BookCreateDto;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.dto.BookUpdateDto;

public interface BookService {
    BookDto getBookByNameV1(String name);
    BookDto createBook(BookCreateDto bookCreateDto);
    BookDto updateBook(BookUpdateDto bookUpdateDto);
    void deleteBook(Long id);
}
