package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayCenterResponseDTO {
    private String centerRegion;
    private String centerLine;
    private String centerStation;
    private String centerComment;
    private String centerHours;
    private String centerTel;
    private String centerAddress;
    private BigDecimal centerLatitude;
    private BigDecimal centerLongitude;
    private Double distance;

    public SubwayCenterResponseDTO(String centerRegion, String centerLine, String centerStation, String centerComment, String centerHours, String centerTel, String centerAddress, BigDecimal centerLatitude, BigDecimal centerLongitude, Double distance) {
        this.centerRegion = centerRegion;
        this.centerLine = centerLine;
        this.centerStation = centerStation;
        this.centerComment = centerComment;
        this.centerHours = centerHours;
        this.centerTel = centerTel;
        this.centerAddress = centerAddress;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.distance = distance;
    }
}
