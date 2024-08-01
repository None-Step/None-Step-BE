package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoElevator {
    private String elevatorComment;

    public InfoElevator(String elevatorComment) {
        this.elevatorComment = elevatorComment;
    }
}
