package site.nonestep.idontwantwalk.board.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@ToString
public class ImgRequestDTO implements Serializable {
    private MultipartFile img;
}
