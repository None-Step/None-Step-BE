package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class BoardWriteResponseDTO {
    private Long boardNo;
    private LocalDateTime boardWriteDate;
}
