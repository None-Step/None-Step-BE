package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoLift {
    private String liftStartFloor;
    private String liftStartComment;

    public InfoLift(String liftStartFloor, String liftStartComment) {
        this.liftStartFloor = liftStartFloor;
        this.liftStartComment = liftStartComment;
    }
}
