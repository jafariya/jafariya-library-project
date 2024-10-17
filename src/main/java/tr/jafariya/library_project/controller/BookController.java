package tr.jafariya.library_project.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {


private final BookService bookService;
    @GetMapping("/book")
    BookDto getBookByNameV1(@RequestParam("name") String name) {
        return bookService.getBookByNameV1(name);
    }

}
