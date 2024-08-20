package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class StationListDTOX {
    private BigDecimal latitude;
    private BigDecimal longitude;

    public StationListDTOX(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
