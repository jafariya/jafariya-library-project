package tr.jafariya.library_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.BookDto;
import tr.jafariya.library_project.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

private final AuthorService authorService;
    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }
/*
    @GetMapping("/v2")
    public AuthorDto getAuthorByNameV2(@RequestParam("name") String name) {
        return authorService.getByNameV2(name);
    } */

    @GetMapping("/author/v1")
    AuthorDto getBookByName(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV1(name);
    }

    @GetMapping("/author/sql")
    AuthorDto getBookByNameV2(@RequestParam("name") String name) {
        return authorService.getByNameV2(name);
    }

    @GetMapping("/author/v3")
    AuthorDto getAuthorByNameV3(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV3(name);
    }


}
