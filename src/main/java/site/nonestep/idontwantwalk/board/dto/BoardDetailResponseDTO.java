package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class BoardDetailResponseDTO {
    private Long boardNo;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardWriteDate;
    private LocalDateTime boardModifyDate;
    private List<String> img;

}
