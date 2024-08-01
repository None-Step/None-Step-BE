package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberPwFindRequestDTO {
    private String memberID;
    private String memberName;
    private String memberPhone;

}
