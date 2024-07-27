package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class MemberIdFindResponseDTO {
    private String memberID;
    private LocalDateTime memberJoinDate;
}
