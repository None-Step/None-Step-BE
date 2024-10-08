package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayClimateCardResponseDTO {
    private String getOn;
    private String getOff;

    public SubwayClimateCardResponseDTO(String getOn, String getOff) {
        this.getOn = getOn;
        this.getOff = getOff;
    }
}
