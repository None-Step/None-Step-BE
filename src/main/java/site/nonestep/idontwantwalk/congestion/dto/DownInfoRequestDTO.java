package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DownInfoRequestDTO {
    private String region;
    private String line;
    private String station;
}
