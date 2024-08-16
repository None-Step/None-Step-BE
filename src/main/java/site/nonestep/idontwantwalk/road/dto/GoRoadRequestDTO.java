package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class GoRoadRequestDTO {
    private BigDecimal goLatitude;
    private BigDecimal goLongitude;
    private String currentRegion;
    private String currentStation;
}
