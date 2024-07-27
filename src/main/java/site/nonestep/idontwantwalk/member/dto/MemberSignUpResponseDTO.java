package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor //회원가입에서만 사용함, 보통은 data, tostring
@Data
@ToString
public class MemberSignUpResponseDTO {
    private String memberID;
    private String memberName;
    private String memberNickname;
    private String memberRandom;
    private LocalDateTime memberJoinDate;

}
