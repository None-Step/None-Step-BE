package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;
import site.nonestep.idontwantwalk.congestion.entity.DayType;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayMarkerRequestDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long radius;
    private String time;
    private DayType type;
}
