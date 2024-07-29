package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayLiftResponseDTO {
    private String liftRegion;
    private String liftLine;
    private String liftStation;
    private String liftExit;
    private String liftAddress;
    private BigDecimal liftLatitude;
    private BigDecimal liftLongitude;
    private String liftStartFloor;
    private String liftStartComment;
    private String liftEndFloor;
    private String liftEndComment;
    private String liftHeight;
    private String liftWidth;
    private String liftKG;
    private Double distance;

    public SubwayLiftResponseDTO(String liftRegion, String liftLine, String liftStation, String liftExit, String liftAddress, BigDecimal liftLatitude, BigDecimal liftLongitude, String liftStartFloor, String liftStartComment, String liftEndFloor, String liftEndComment, String liftHeight, String liftWidth, String liftKG, Double distance) {
        this.liftRegion = liftRegion;
        this.liftLine = liftLine;
        this.liftStation = liftStation;
        this.liftExit = liftExit;
        this.liftAddress = liftAddress;
        this.liftLatitude = liftLatitude;
        this.liftLongitude = liftLongitude;
        this.liftStartFloor = liftStartFloor;
        this.liftStartComment = liftStartComment;
        this.liftEndFloor = liftEndFloor;
        this.liftEndComment = liftEndComment;
        this.liftHeight = liftHeight;
        this.liftWidth = liftWidth;
        this.liftKG = liftKG;
        this.distance = distance;
    }
}
