package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DaejeonBikeDTO {
    private double currentLatitude;
    private double currentLongitude;
    private String goRegion;
    private String goStation;
}
