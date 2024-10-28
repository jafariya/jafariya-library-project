package tr.jafariya.library_project.controller;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tr.jafariya.library_project.dto.GenreDto;
import tr.jafariya.library_project.service.BookService;
import tr.jafariya.library_project.service.GenreService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    String getGenresView(Model model) {
        List<GenreDto> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genres";
    }
}
