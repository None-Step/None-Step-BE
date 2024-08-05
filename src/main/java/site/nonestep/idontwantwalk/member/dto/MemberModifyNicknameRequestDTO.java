package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
public class MemberModifyNicknameRequestDTO implements Serializable {
    private String memberNickname;
    private MultipartFile memberIMG;
}
