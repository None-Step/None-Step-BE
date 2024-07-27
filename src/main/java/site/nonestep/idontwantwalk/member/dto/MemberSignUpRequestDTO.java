package site.nonestep.idontwantwalk.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class MemberSignUpRequestDTO {
    @NotBlank //빈칸이면 안돼
    private String memberID;
    @NotBlank
    private String memberPass;
    @NotBlank
    private String memberName;
    private String memberMail;
    @NotBlank
    private String memberPhone;
}
