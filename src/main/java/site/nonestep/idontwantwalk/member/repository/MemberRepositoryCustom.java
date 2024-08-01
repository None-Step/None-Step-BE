package site.nonestep.idontwantwalk.member.repository;

import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.entity.SocialType;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    //회원가입 시 id 중복검사
    Optional<String> selectMemberID(String memberID);

    // 소셜 로그인
    // MemberNo로 Token값 가져옴
    Optional<String> selectTokenByMemberNo(Long memberNo);

    // 소셜ID로 MemberNo를 가져온다.
    Optional<Long> selectMemberBySocialID(SocialType registrationID, String socialID);

    //id 찾기
    List<Member> selectMemberNameAndMemberPhone(String memberName, String memberPhone);
    //memberid를 찾기 위해서 조회해야 할 것 name,phone을 변수이름으로, (자료타입 + 변수명)=> 2개 적어야 할 때는 괄호 안에 동일하게 적기

    //pw 찾기
    String selectMemberPwFind(String memberID, String memberName, String memberPhone);

    //일반로그인
    Long selectMemberIdAndMemberPass(String memberID, String memberPass);


    //마이페이지 조회
    //많은 정보를 받기 때문(자료타입이 다르기 때문)에 member entity를 받아온다.
    Optional<Member> selectMemberInfo(Long memberNo);

    //다른유저 프로필 조회
    Optional<Member> selectMemberOther(String memberNickName, String memberRandom);
}
