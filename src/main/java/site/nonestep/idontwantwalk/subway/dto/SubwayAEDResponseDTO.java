package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayAEDResponseDTO {
    private String aedRegion;
    private String aedLine;
    private String aedStation;
    private String aedComment;
    private String aedAddress;
    private BigDecimal aedLatitude;
    private BigDecimal aedLongitude;
    private Double distance;

    public SubwayAEDResponseDTO(String aedRegion, String aedLine, String aedStation, String aedComment, String aedAddress, BigDecimal aedLatitude, BigDecimal aedLongitude, Double distance) {
        this.aedRegion = aedRegion;
        this.aedLine = aedLine;
        this.aedStation = aedStation;
        this.aedComment = aedComment;
        this.aedAddress = aedAddress;
        this.aedLatitude = aedLatitude;
        this.aedLongitude = aedLongitude;
        this.distance = distance;
    }
}
