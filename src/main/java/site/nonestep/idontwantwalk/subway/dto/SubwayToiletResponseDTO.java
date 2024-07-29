package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayToiletResponseDTO {
    private String toiletRegion;
    private String toiletLine;
    private String toiletStation;
    private String toiletExit;
    private String toiletComment;
    private String toiletAddress;
    private BigDecimal toiletLatitude;
    private BigDecimal toiletLongitude;
    private Double distance;

    public SubwayToiletResponseDTO(String toiletRegion, String toiletLine, String toiletStation, String toiletExit, String toiletComment, String toiletAddress, BigDecimal toiletLatitude, BigDecimal toiletLongitude, Double distance) {
        this.toiletRegion = toiletRegion;
        this.toiletLine = toiletLine;
        this.toiletStation = toiletStation;
        this.toiletExit = toiletExit;
        this.toiletComment = toiletComment;
        this.toiletAddress = toiletAddress;
        this.toiletLatitude = toiletLatitude;
        this.toiletLongitude = toiletLongitude;
        this.distance = distance;
    }
}
