package site.nonestep.idontwantwalk.bookmark.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayDeleteRequestDTO {
    private String region;
    private String line;
    private String station;
}
