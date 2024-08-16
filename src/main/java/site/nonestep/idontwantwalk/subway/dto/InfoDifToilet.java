package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoDifToilet {
    private String difToiletExit;
    private String difToiletComment;

    public InfoDifToilet(String difToiletExit, String difToiletComment) {
        this.difToiletExit = difToiletExit;
        this.difToiletComment = difToiletComment;
    }
}
