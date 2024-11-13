package tr.jafariya.library_project.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.jafariya.library_project.dto.AuthorCreateDto;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.AuthorUpdateDto;
import tr.jafariya.library_project.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

private final AuthorService authorService;
    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author/v1")
    AuthorDto getAuthorByNameV1(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV1(name);
    }

    @GetMapping("/author/sql")
    AuthorDto getAuthorByNameV2(@RequestParam("name") String name) {
        return authorService.getByNameV2(name);
    }

    @GetMapping("/author/surname")
    AuthorDto getAuthorBySurname(@RequestParam("surname") String surname) {
        return authorService.getAuthorBySurname(surname);
    }

    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody @Valid AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/update")
    AuthorDto updateAuthor(@RequestBody @Valid AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("/author/delete/{id}")
    void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }


}
