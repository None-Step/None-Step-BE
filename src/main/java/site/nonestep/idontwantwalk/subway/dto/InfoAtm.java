package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoAtm {
    private String atmComment;

    public InfoAtm(String atmComment) {
        this.atmComment = atmComment;
    }
}
