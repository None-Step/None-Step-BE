package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoEscal {
    private String escalComment;

    public InfoEscal(String escalComment) {
        this.escalComment = escalComment;
    }
}
