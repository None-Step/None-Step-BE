package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayChargerResponseDTO {
    private String chargerRegion;
    private String chargerLine;
    private String chargerStation;
    private String chargerComment;
    private String chargerAddress;
    private BigDecimal chargerLatitude;
    private BigDecimal chargerLongitude;
    private Double distance;

    public SubwayChargerResponseDTO(String chargerRegion, String chargerLine, String chargerStation, String chargerComment, String chargerAddress, BigDecimal chargerLatitude, BigDecimal chargerLongitude, Double distance) {
        this.chargerRegion = chargerRegion;
        this.chargerLine = chargerLine;
        this.chargerStation = chargerStation;
        this.chargerComment = chargerComment;
        this.chargerAddress = chargerAddress;
        this.chargerLatitude = chargerLatitude;
        this.chargerLongitude = chargerLongitude;
        this.distance = distance;
    }
}
