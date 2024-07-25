package site.nonestep.idontwantwalk.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static site.nonestep.idontwantwalk.member.entity.QMember.member; //jpa q속성: member entity 사용할 때 씀,
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    //회원가입시 id 중복체크
    @Override
    public Optional<String> selectMemberID(String memberID) {
        return Optional.ofNullable(

                queryFactory.select(member.memberID)
                        .from(member)
                        .where(member.memberID.eq(memberID))
                        .fetchFirst()
        );
    }
}
