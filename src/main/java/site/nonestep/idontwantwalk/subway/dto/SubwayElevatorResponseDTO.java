package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayElevatorResponseDTO {
    private String elevatorRegion;
    private String elevatorLine;
    private String elevatorStation;
    private String elevatorComment;
    private String elevatorAddress;
    private BigDecimal elevatorLatitude;
    private BigDecimal elevatorLongitude;
    private Double distance;

    public SubwayElevatorResponseDTO(String elevatorRegion, String elevatorLine, String elevatorStation, String elevatorComment, String elevatorAddress, BigDecimal elevatorLatitude, BigDecimal elevatorLongitude, Double distance) {
        this.elevatorRegion = elevatorRegion;
        this.elevatorLine = elevatorLine;
        this.elevatorStation = elevatorStation;
        this.elevatorComment = elevatorComment;
        this.elevatorAddress = elevatorAddress;
        this.elevatorLatitude = elevatorLatitude;
        this.elevatorLongitude = elevatorLongitude;
        this.distance = distance;
    }
}
