package site.nonestep.idontwantwalk.member.repository;

import java.util.Optional;

public interface MemberRepositoryCustom {
    //회원가입 시 id 중복검사
    Optional<String> selectMemberID(String memberID);
}
