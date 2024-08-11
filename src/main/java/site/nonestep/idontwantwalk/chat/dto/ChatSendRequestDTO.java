package site.nonestep.idontwantwalk.chat.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatSendRequestDTO {
    private String region;
    private String line;
    private String message;
    private Long chatReply;
}