package site.nonestep.idontwantwalk.chat.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatBeforeLineRequestDTO {
    private String region;
    private String line;

}
