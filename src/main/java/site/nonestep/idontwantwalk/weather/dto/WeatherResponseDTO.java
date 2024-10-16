package site.nonestep.idontwantwalk.weather.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WeatherResponseDTO  {
    String baseDate;
    String baseTime;
    String category;
    String fcstDate;
    String fcstTime;
    String fcstValue;
    Long nx;
    Long ny;
}
