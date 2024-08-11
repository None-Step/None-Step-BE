package site.nonestep.idontwantwalk.chat.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ChatRegionLineSelectAfterResponseDTO {
    private Long chatNo;
    private String message;
    private LocalDateTime date;
    private String memberNickName;
    private String memberRandom;
    private String memberIMG;
    private Long chatReply;
}
