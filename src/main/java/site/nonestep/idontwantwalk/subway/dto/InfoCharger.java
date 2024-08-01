package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InfoCharger {
    private String chargerComment;

    public InfoCharger(String chargerComment) {
        this.chargerComment = chargerComment;
    }
}
