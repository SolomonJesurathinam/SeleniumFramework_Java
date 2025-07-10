package api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
public class NoteData {
    private String id;
    private String title;
    private String description;
    private String category;
    private boolean completed;
    private String created_at;
    private String updated_at;
    private String user_id;
}
