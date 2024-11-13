package tr.jafariya.library_project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tr.jafariya.library_project.service.BookServiceImpl;
import tr.jafariya.library_project.dto.AuthorDto;
import tr.jafariya.library_project.dto.BookDto;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBookByNameV1() throws Exception {
        String name = "The Call of the Wild";

        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name("Jack")
                .surname("London")
                .build();

        BookDto bookDto = BookDto.builder()
                .id(1L)
                .name(name)
                .genre("Adventure")
                .authors(Collections.singletonList(authorDto))
                .build();


        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].id").value(bookDto.getAuthors().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].name").value(bookDto.getAuthors().get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].surname").value(bookDto.getAuthors().get(0).getSurname()));
    }




}
