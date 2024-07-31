package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberLoginRequestDTO {
    private String memberID;
    private String memberPass;

}
