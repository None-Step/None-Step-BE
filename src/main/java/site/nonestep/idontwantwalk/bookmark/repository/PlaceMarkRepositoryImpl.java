package site.nonestep.idontwantwalk.bookmark.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.bookmark.dto.PlaceListResponseDTO;

import java.math.BigDecimal;
import java.util.List;

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

    // [장소] 즐겨 찾기 조회
    @Override
    public List<PlaceListResponseDTO> selectPlaceList(Long memberNo) {
        return queryFactory.select(Projections.constructor(PlaceListResponseDTO.class, placeMark.placeNo, placeMark.placeNickName,
                        placeMark.placeLatitude, placeMark.placeLongitude, placeMark.placeAddress, placeMark.placeColor))
                .from(placeMark)
                .where(placeMark.memberNo.memberNo.eq(memberNo))
                .fetch();
    }

    // [장소] 즐겨 찾기 삭제
    @Override
    public void deletePlace(Long placeNo) {
        queryFactory.delete(placeMark)
                .where(placeMark.placeNo.eq(placeNo))
                .execute();
    }

    // [장소] 동일한 장소를 즐겨 찾기 했는지 조회
    // Count로 조회해서 0이 아니라면 중복으로 등록하려는 경우이기 때문에 null을 return한다.
    @Override
    public Long selectSamePlace(BigDecimal latitude, BigDecimal longitude, Long memberNo) {
        return queryFactory.select(placeMark.placeNo.count())
                .from(placeMark)
                .where(placeMark.placeLatitude.eq(latitude).and(placeMark.placeLongitude.eq(longitude).and(placeMark.memberNo.memberNo.eq(memberNo))))
                .fetchFirst();
    }


}
