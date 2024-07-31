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

    //일반로그인
    Long login(String memberID, String memberPass); //우선 자료타입과 변수명을 적은 후 어떤 값을 받아오는지 생각해보기(
    //일반로그인 시 DB에 Refresh Token을 저장
    void refreshlogin (Long memberNo, String memberRefreshToken);

}

