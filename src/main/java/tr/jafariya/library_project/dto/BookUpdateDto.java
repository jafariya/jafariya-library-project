package tr.jafariya.library_project.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class BookUpdateDto {

    String name;
    String genreName;
    private List<Long> authorIds;
    Long id;
}
