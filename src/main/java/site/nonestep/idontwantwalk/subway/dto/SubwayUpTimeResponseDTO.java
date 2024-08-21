package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayUpTimeResponseDTO {
    private String direction;
    private String weekdayStart;
    private String weekdayEnd;
    private String satStart;
    private String satEnd;
    private String holidayStart;
    private String holidayEnd;

    public SubwayUpTimeResponseDTO(String direction, String weekdayStart, String weekdayEnd, String satStart, String satEnd, String holidayStart, String holidayEnd) {
        this.direction = direction;
        this.weekdayStart = weekdayStart;
        this.weekdayEnd = weekdayEnd;
        this.satStart = satStart;
        this.satEnd = satEnd;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
    }
}
