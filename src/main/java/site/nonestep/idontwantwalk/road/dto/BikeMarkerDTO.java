package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class BikeMarkerDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;

    public BikeMarkerDTO(){}

    public BikeMarkerDTO(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
