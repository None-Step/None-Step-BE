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

    //pw 찾기
    @Override
    public String selectMemberPwFind(String memberID, String memberName, String memberPhone) {
        return queryFactory.select(member.memberPassword)
                .from(member)
                .where(member.memberID.eq(memberID).and(member.memberName.eq(memberName).and(member.memberPhone.eq(memberPhone))))
                .fetchFirst();
    }

    //일반로그인
    @Override
    public Member selectMemberIdAndMemberPass(String memberID) {

        return queryFactory.select(member)
                .from(member)
                .where(member.memberID.eq(memberID).and(member.memberIsDelete.eq(false)))
                .fetchFirst();
    }

    //마이페이지 조회 , 여기서는 member전체를 조회하고, serviceImpl에서 필요한 애들만 보낸다.
    @Override
    public Optional<Member> selectMemberInfo(Long memberNo) {
        return Optional.ofNullable( //optional은 괄호 안에 queryfactory사용함
                queryFactory.select(member)
                        .from(member)
                        .where(member.memberNo.eq(memberNo))
                        .fetchFirst()
        );
    }


    //다른유저 프로필 조회
    @Override
    public Optional<Member> selectMemberOther(String memberNickName, String memberRandom) {
        return Optional.ofNullable(
                queryFactory.select(member)
                        .from(member)
                        .where(member.memberNickName.eq(memberNickName).and(member.memberRandom.eq(memberRandom)))
                        .fetchFirst()
        );
    }

    // Refresh Token과 ID가 같은지 확인
    @Override
    public Optional<Long> memberRefreshTokenAndID(String memberID, String memberToken) {
        return Optional.ofNullable(
                queryFactory.select(member.memberNo)
                        .from(member)
                        .where(member.memberID.eq(memberID).and(member.memberRefreshToken.eq(memberToken)))
                        .fetchFirst()
        );
    }

    // 비밀번호 변경, 혹은 찾을 때 받는 정보로 Member 조회
    @Override
    public Optional<Member> selectPass(String memberID, String memberName, String memberPhone) {
        return Optional.ofNullable(
                queryFactory.select(member)
                        .from(member)
                        .where(member.memberID.eq(memberID).and(member.memberName.eq(memberName).and(member.memberPhone.eq(memberPhone))))
                        .fetchFirst()
        );
    }

}
