package tr.jafariya.library_project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tr.jafariya.library_project.dto.AuthorCreateDto;
import tr.jafariya.library_project.dto.AuthorDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) //turn off authentification

public class AuthorRestControllerTest {
    @Autowired
    private MockMvc mockMvc; //lets us to test by url and test all app

    @Test
    public void testGetAuthorById() throws Exception {
        Long id = 1L;
        AuthorDto authorDto = AuthorDto.builder()
                .id(id)
                .name("Jack")
                .surname("London")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorByNameV1() throws Exception {
        String name = "Jack";
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name(name)
                .surname("London")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v1")
                .param("name", name)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorByNameV2() throws Exception {
        String name = "Jack";
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name(name)
                .surname("London")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/author/sql")
                        .param("name", name)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorBySurname() throws Exception {
        String surname = "London";
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name("Jack")
                .surname(surname)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/author/surname")
                        .param("surname", surname)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }



}
