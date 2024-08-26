package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoListResponseDTO {
    private String type;
    private Long time;
    private Long distance;
}
