package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayFloodingRequestDTO {
    private String region;
    private String line;
    private String station;
}
