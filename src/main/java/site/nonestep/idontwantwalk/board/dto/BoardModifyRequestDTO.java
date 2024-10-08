package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;


@Data
@ToString
public class BoardModifyRequestDTO implements Serializable {
    private Long boardNo;
    private String boardTitle;
    private String boardContent;
    private List<MultipartFile> img;

}
