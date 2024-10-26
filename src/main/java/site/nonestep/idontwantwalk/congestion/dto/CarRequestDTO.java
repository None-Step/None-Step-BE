package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;
import site.nonestep.idontwantwalk.congestion.entity.DayType;

import java.time.LocalTime;

@Data
@ToString
public class CarRequestDTO {
    String region;
    String line;
    String station;
    DayType type;
    LocalTime time;
}
