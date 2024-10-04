package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardWriteRequestDTO {
    private String boardTitle;
    private String boardContent;
}
