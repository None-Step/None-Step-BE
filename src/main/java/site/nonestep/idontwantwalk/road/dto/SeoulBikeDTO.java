package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SeoulBikeDTO {
    private double currentLatitude;
    private double currentLongitude;
    private String goRegion;
    private String goStation;
}
