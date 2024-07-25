package site.nonestep.idontwantwalk.member.service;

import site.nonestep.idontwantwalk.member.dto.MemberIdCheckRequestDTO;
import site.nonestep.idontwantwalk.member.dto.MemberSignUpRequestDTO;
import site.nonestep.idontwantwalk.member.dto.MemberSignUpResponseDTO;

public interface MemberService {
    //회원가입
    MemberSignUpResponseDTO signUp(MemberSignUpRequestDTO memberSignUpRequestDTO);

    //회원가입시 id 중복체크
    Boolean memberIdCheck (MemberIdCheckRequestDTO memberIdCheckRequestDTO);

}

