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

    //pw 찾기
     MemberPwFindResponseDTO pwFind(MemberPwFindRequestDTO memberPwFindRequestDTO);

     //마이페이지 조회
    MemberInfoResponseDTO info(Long memberNo);//request가 없는 경우 memberno로만 주고 받는다고 생각하면 됨

    //다른 유저 프로필 조회
    MemberOthersResponseDTO others(MemberOthersRequestDTO memberOthersRequestDTO);

    //프로필편집: 휴대폰변경
    MemberModifyPhoneResponseDTO modifyPhone(MemberModifyPhoneRequestDTO memberModifyPhoneRequestDTO, Long memberNo);
    //프로필편집은 회원인 상태이기 때문에 토큰을 가지고 있다. 따라서 멤버넘버까지도 받아와야한다.

    //프로필편집: 메일 변경
    MemberModifyMailResponseDTO modifyMail(MemberModifyMailRequestDTO memberModifyMailRequestDTO, Long memberNo);
}

