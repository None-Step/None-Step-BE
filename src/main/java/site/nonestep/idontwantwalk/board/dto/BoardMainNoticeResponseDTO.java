package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardMainNoticeResponseDTO {
    private Long boardNo;
    private String boardTitle;

    public BoardMainNoticeResponseDTO(Long boardNo, String boardTitle) {
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
    }
}
