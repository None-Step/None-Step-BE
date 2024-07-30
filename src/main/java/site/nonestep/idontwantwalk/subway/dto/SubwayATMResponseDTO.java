package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayATMResponseDTO {
    private String atmRegion;
    private String atmLine;
    private String atmStation;
    private String atmComment;
    private String atmAddress;
    private BigDecimal atmLatitude;
    private BigDecimal atmLongitude;
    private Double distance;

    public SubwayATMResponseDTO(String atmRegion, String atmLine, String atmStation, String atmComment, String atmAddress, BigDecimal atmLatitude, BigDecimal atmLongitude, Double distance) {
        this.atmRegion = atmRegion;
        this.atmLine = atmLine;
        this.atmStation = atmStation;
        this.atmComment = atmComment;
        this.atmAddress = atmAddress;
        this.atmLatitude = atmLatitude;
        this.atmLongitude = atmLongitude;
        this.distance = distance;
    }
}
