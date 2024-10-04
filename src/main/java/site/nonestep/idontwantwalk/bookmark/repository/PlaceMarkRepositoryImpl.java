package site.nonestep.idontwantwalk.bookmark.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static site.nonestep.idontwantwalk.bookmark.entity.QPlaceMark.*;
import static site.nonestep.idontwantwalk.member.entity.QMember.*;

public class PlaceMarkRepositoryImpl implements PlaceMarkRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // [장소] 즐겨찾기 등록 전, 회원의 즐겨 찾기 갯수가 일정 갯수를 넘는지 조회
    @Override
    public Long selectPlaceMarkCount(Long memberNo) {
        return queryFactory.select(placeMark.placeNickName.count())
                .from(placeMark)
                .where(placeMark.memberNo.memberNo.eq(memberNo))
                .fetchFirst();
    }
}
