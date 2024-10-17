package tr.jafariya.library_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.repository.BookRepository;
import tr.jafariya.library_project.model.Book;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Override
    public BookDto getBookByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .name(book.getName())
                .genre(book.getGenre().getName())
                .id(book.getId())
                .build();
    }

}
