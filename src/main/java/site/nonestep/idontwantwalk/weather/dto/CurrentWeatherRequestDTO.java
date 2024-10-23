package site.nonestep.idontwantwalk.weather.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class CurrentWeatherRequestDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;

}
