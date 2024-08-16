package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class GoStationRequestDTO {
    private BigDecimal currentLatitude;
    private BigDecimal currentLongitude;
    private String goRegion;
    private String goStation;

}
