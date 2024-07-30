package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayDifToiletResponseDTO {
    private String difToiletRegion;
    private String difToiletLine;
    private String difToiletStation;
    private String difToiletExit;
    private String difToiletComment;
    private String difToiletAddress;
    private BigDecimal difToiletLatitude;
    private BigDecimal difToiletLongitude;
    private Double distance;

    public SubwayDifToiletResponseDTO(String difToiletRegion, String difToiletLine, String difToiletStation, String difToiletExit, String difToiletComment, String difToiletAddress, BigDecimal difToiletLatitude, BigDecimal difToiletLongitude, Double distance) {
        this.difToiletRegion = difToiletRegion;
        this.difToiletLine = difToiletLine;
        this.difToiletStation = difToiletStation;
        this.difToiletExit = difToiletExit;
        this.difToiletComment = difToiletComment;
        this.difToiletAddress = difToiletAddress;
        this.difToiletLatitude = difToiletLatitude;
        this.difToiletLongitude = difToiletLongitude;
        this.distance = distance;
    }
}
