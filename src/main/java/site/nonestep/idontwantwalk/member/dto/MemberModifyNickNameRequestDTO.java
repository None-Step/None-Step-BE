package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@ToString
public class MemberModifyNickNameRequestDTO implements Serializable {
    private String memberNickName;
    private MultipartFile memberIMG;
}
