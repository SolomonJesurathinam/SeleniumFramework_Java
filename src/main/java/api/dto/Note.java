package api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(setterPrefix = "set")
public class Note {

    private String title;
    private String description;
    private String category;

}
