package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayNursingResponseDTO {
    private String nursingRegion;
    private String nursingLine;
    private String nursingStation;
    private String nursingComment;
    private String nursingAddress;
    private BigDecimal nursingLatitude;
    private BigDecimal nursingLongitude;
    private Double distance;

    public SubwayNursingResponseDTO(String nursingRegion, String nursingLine, String nursingStation, String nursingComment, String nursingAddress, BigDecimal nursingLatitude, BigDecimal nursingLongitude, Double distance) {
        this.nursingRegion = nursingRegion;
        this.nursingLine = nursingLine;
        this.nursingStation = nursingStation;
        this.nursingComment = nursingComment;
        this.nursingAddress = nursingAddress;
        this.nursingLatitude = nursingLatitude;
        this.nursingLongitude = nursingLongitude;
        this.distance = distance;
    }
}
