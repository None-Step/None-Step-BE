package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class MemberInfoResponseDTO {
    private String memberID;
    private String memberMail;
    private String memberName;
    private String memberPhone;
    private String memberIMG;
    private String memberNickName;
    private String memberRandom;
    private LocalDateTime memberJoinDate;
}
