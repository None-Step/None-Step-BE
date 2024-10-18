package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SubwayMarkerResponseDTO {
    private String region;
    private String line;
    private String station;
    private String upCongestion;
    private String downCongestion;
    private String upNextStation;
    private String downNextStation;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
