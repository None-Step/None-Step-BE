package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayNowRequestDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
