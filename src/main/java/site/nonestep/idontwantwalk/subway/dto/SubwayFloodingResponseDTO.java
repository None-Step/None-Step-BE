package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayFloodingResponseDTO {
    private String isFlooding;

    public SubwayFloodingResponseDTO(String isFlooding) {
        this.isFlooding = isFlooding;
    }
}
