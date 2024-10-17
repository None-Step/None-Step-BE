package site.nonestep.idontwantwalk.congestion.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DownInfoResponseDTO {
    private Long no;
    private String state;
    private String elevator;
    private String escal;
    private String stair;
    private String best;


    public DownInfoResponseDTO(Long no, String state, String elevator, String escal, String stair, String best) {
        this.no = no;
        this.state = state;
        this.elevator = elevator;
        this.escal = escal;
        this.stair = stair;
        this.best = best;
    }
}
