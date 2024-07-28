package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayLocationResponseDTO {

    private String infoRegion;
    private String infoLine;
    private String infoStation;
    private String infoAddress;
    private BigDecimal infoLatitude;
    private BigDecimal infoLongitude;
    private Double distance;

    public SubwayLocationResponseDTO(String infoRegion, String infoLine, String infoStation, String infoAddress, BigDecimal infoLatitude, BigDecimal infoLongitude, Double distance) {
        this.infoRegion = infoRegion;
        this.infoLine = infoLine;
        this.infoStation = infoStation;
        this.infoAddress = infoAddress;
        this.infoLatitude = infoLatitude;
        this.infoLongitude = infoLongitude;
        this.distance = distance;
    }
}
