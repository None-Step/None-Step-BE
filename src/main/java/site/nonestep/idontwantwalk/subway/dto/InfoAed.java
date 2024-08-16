package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoAed {
    private String aedComment;

    public InfoAed(String aedComment) {
        this.aedComment = aedComment;
    }
}
