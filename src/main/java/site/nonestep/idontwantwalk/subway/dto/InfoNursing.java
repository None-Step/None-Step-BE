package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoNursing {
    private String nursingRoomComment;

    public InfoNursing(String nursingRoomComment) {
        this.nursingRoomComment = nursingRoomComment;
    }
}
