package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class MemberOthersResponseDTO {
    private String memberNickName;
    private String memberRandom;
    private String memberFile;
    private LocalDateTime memberJoinDate;
}
