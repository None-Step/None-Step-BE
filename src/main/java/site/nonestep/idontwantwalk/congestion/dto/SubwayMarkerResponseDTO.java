package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayMarkerResponseDTO {
    private String region;
    private String line;
    private String station;
    private String upCongestion;
    private String downCongestion;
    private String upNextStation;
    private String downNextStation;

}
