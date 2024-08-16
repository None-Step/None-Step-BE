package site.nonestep.idontwantwalk.chat.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatDeleteDTO {
    private Long chatNo;
    private String region;
    private String line;
}
