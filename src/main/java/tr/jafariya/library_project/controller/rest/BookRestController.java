package tr.jafariya.library_project.controller.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.jafariya.library_project.dto.*;
import tr.jafariya.library_project.service.BookService;
import org.springframework.ui.Model;

@RestController
@RequiredArgsConstructor
public class BookRestController {


private final BookService bookService;

    @PostMapping("/book/create")
    BookDto createAuthor(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @GetMapping("/book")
    BookDto getBookByNameV1(@RequestParam("name") String name) {
        return bookService.getBookByNameV1(name);
    }

    @PutMapping("/book/update")
    BookDto updateBook(@RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("/book/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }


}
