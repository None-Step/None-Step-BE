package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class BoardListResponseDTO {
    private Long boardNo;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardWriteDate;
    private LocalDateTime boardModifyDate;

    public BoardListResponseDTO(Long boardNo, String boardTitle, String boardContent, LocalDateTime boardWriteDate, LocalDateTime boardModifyDate) {
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriteDate = boardWriteDate;
        this.boardModifyDate = boardModifyDate;
    }
}
