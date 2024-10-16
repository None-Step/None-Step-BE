package site.nonestep.idontwantwalk.weather.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class WeatherRequestDTO {
    private Long x;
    private Long y;
    private String baseDate;
    private String baseTime;
}
