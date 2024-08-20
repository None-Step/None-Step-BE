package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayPathRequestDTO {
    private String region;
    private String startLine;
    private String startStation;
    private String endLine;
    private String endStation;
}
