package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoRoadRequestDTO {
    private String currentLatitude;
    private String currentLongitude;
    private String currentRegion;
    private String currentLine;
    private String currentStation;
}
