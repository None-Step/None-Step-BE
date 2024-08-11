package site.nonestep.idontwantwalk.chat.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ChatListResponseDTO {
    private String line;
    private LocalDateTime date;

    public ChatListResponseDTO(String line, LocalDateTime date) {
        this.line = line;
        this.date = date;
    }
}
