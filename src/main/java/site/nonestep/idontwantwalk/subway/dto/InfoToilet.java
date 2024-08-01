package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoToilet {
    private String toiletExit;
    private String toiletComment;

    public InfoToilet(String toiletExit, String toiletComment) {
        this.toiletExit = toiletExit;
        this.toiletComment = toiletComment;
    }
}
