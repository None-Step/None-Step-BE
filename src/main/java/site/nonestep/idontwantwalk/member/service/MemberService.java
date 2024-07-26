package site.nonestep.idontwantwalk.member.service;

import site.nonestep.idontwantwalk.member.dto.*;

import java.util.List;

public interface MemberService {
    //회원가입
    MemberSignUpResponseDTO signUp(MemberSignUpRequestDTO memberSignUpRequestDTO);

    //회원가입시 id 중복체크
    Boolean memberIdCheck (MemberIdCheckRequestDTO memberIdCheckRequestDTO);

    //id 찾기
    List<MemberIdFindResponseDTO> idFind (MemberIdFindRequestDTO memberIdFindRequestDTO); //여러개의 정보가 나와서 list로 가져옴
}

