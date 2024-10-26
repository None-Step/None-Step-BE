package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Data
@ToString
public class CarResponseDTO {
    LocalTime localTime;
    List<String> congestion;
}
