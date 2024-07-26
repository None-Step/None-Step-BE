package site.nonestep.idontwantwalk.member.repository;

import site.nonestep.idontwantwalk.member.entity.SocialType;

import java.util.Optional;

public interface MemberRepositoryCustom {
    //회원가입 시 id 중복검사
    Optional<String> selectMemberID(String memberID);

    // 소셜 로그인
    // MemberNo로 Token값 가져옴
    Optional<String> selectTokenByMemberNo(Long memberNo);

    // 소셜ID로 MemberNo를 가져온다.
    Optional<Long> selectMemberBySocialID(SocialType registrationID, String socialID);
}
