package site.nonestep.idontwantwalk.chat.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ChatBeforeAllResponseDTO {
    private Long chatNo;
    private String chatLine;
    private String message;
    private LocalDateTime date;
    private String memberNickName;
    private String memberRandom;
    private String memberIMG;
    private Long chatReply;
    private Boolean isChatDelete;

    public ChatBeforeAllResponseDTO(Long chatNo, String chatLine, String message, LocalDateTime date, String memberNickName, String memberRandom, String memberIMG, Long chatReply, Boolean isChatDelete) {
        this.chatNo = chatNo;
        this.chatLine = chatLine;
        this.message = message;
        this.date = date;
        this.memberNickName = memberNickName;
        this.memberRandom = memberRandom;
        this.memberIMG = memberIMG;
        this.chatReply = chatReply;
        this.isChatDelete = isChatDelete;
    }
}
