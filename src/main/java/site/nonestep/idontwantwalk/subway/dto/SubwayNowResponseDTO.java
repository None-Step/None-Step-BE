package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayNowResponseDTO {
    private String region;
    private String line;
    private String station;
    private Double distance;

    public SubwayNowResponseDTO(String region, String line, String station, Double distance) {
        this.region = region;
        this.line = line;
        this.station = station;
        this.distance = distance;
    }
}
