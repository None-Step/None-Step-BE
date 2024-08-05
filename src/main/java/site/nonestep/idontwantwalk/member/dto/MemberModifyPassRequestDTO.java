package site.nonestep.idontwantwalk.member.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberModifyPassRequestDTO {
    private String memberID;
    private String memberName;
    private String memberPhone;
    private String memberPass;

}
