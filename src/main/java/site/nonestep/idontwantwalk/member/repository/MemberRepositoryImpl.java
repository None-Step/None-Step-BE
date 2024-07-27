package site.nonestep.idontwantwalk.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.entity.SocialType;

import java.util.List;
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

    // Token 받아오기
    @Override
    public Optional<String> selectTokenByMemberNo(Long memberNo) {
        return Optional.ofNullable(
                queryFactory.select(member.memberRefreshToken)
                        .from(member)
                        .where(member.memberNo.eq(memberNo))
                        .fetchFirst());
    }

    // 소셜 로그인시, KAKAO, NAVER 중 무엇인지 파악한 뒤, 첫 로그인인지 판별
    @Override
    public Optional<Long> selectMemberBySocialID(SocialType registrationID, String socialID) {
        return Optional.ofNullable(
                queryFactory.select(member.memberNo)
                        .from(member)
                        .where(member.memberSocialID.eq(socialID).and(member.memberSocialType.eq(registrationID)))
                        .fetchFirst()); //결과가 하나만 나올 때
    }

    //id 찾기
    @Override
    public List<Member> selectMemberNameAndMemberPhone(String memberName, String memberPhone) {
        return queryFactory.select(member)
                .from(member)
                .where(member.memberName.eq(memberName).and(member.memberPhone.eq(memberPhone)))
                .fetch();//결과가 여러개 나올 때
    }
}