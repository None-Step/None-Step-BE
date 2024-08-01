package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoCenter {
    private String centerComment;
    private String centerHours;
    private String centerTel;

    public InfoCenter(String centerComment, String centerHours, String centerTel) {
        this.centerComment = centerComment;
        this.centerHours = centerHours;
        this.centerTel = centerTel;
    }
}
