package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class BoardModifyRequestDTO implements Serializable {
    private Long boardNo;
    private String boardTitle;
    private String boardContent;
}
