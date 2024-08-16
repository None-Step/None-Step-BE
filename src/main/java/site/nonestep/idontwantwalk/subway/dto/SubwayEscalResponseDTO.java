package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayEscalResponseDTO {
    private String escalRegion;
    private String escalLine;
    private String escalStation;
    private String escalComment;
    private String escalAddress;
    private BigDecimal escalLatitude;
    private BigDecimal escalLongitude;
    private Double distance;

    public SubwayEscalResponseDTO(String escalRegion, String escalLine, String escalStation, String escalComment, String escalAddress, BigDecimal escalLatitude, BigDecimal escalLongitude, Double distance) {
        this.escalRegion = escalRegion;
        this.escalLine = escalLine;
        this.escalStation = escalStation;
        this.escalComment = escalComment;
        this.escalAddress = escalAddress;
        this.escalLatitude = escalLatitude;
        this.escalLongitude = escalLongitude;
        this.distance = distance;
    }
}
