package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;
import site.nonestep.idontwantwalk.congestion.entity.DayType;

@Data
@ToString
public class DownTimeRequestDTO {
    private String region;
    private String line;
    private String station;
    private String time;
    private DayType type;
}
