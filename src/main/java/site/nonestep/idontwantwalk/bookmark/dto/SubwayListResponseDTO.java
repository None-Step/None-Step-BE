package site.nonestep.idontwantwalk.bookmark.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayListResponseDTO {
    private String region;
    private String line;
    private String station;

    public SubwayListResponseDTO(String region, String line, String station) {
        this.region = region;
        this.line = line;
        this.station = station;
    }
}
