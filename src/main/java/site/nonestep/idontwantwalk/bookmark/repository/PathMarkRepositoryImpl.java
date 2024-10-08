package site.nonestep.idontwantwalk.bookmark.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.bookmark.dto.PathListResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import static site.nonestep.idontwantwalk.member.entity.QMember.*;
import static site.nonestep.idontwantwalk.bookmark.entity.QPathMark.*;

public class PathMarkRepositoryImpl implements PathMarkRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // [경로] 즐겨 찾기 등록 전, 5개가 넘는지 확인하기 위해 조회
    @Override
    public Long selectPathMarkCount(Long memberNo) {
        return queryFactory.select(pathMark.pathNo.count())
                .from(pathMark)
                .where(pathMark.memberNo.memberNo.eq(memberNo))
                .fetchFirst();
    }

    // [경로] 즐겨 찾기 조회
    @Override
    public List<PathListResponseDTO> selectPathList(Long memberNo) {
        return queryFactory.select(Projections.constructor(PathListResponseDTO.class, pathMark.pathNo, pathMark.pathStartNickName,
                pathMark.pathEndNickName, pathMark.pathStartLatitude, pathMark.pathStartLongitude, pathMark.pathEndLatitude,
                pathMark.pathEndLongitude, pathMark.pathColor))
                .from(pathMark)
                .where(pathMark.memberNo.memberNo.eq(memberNo))
                .fetch();
    }

    // [경로] 즐겨 찾기 삭제
    @Override
    public void deletePath(Long pathNo) {
        queryFactory.delete(pathMark)
                .where(pathMark.pathNo.eq(pathNo))
                .execute();
    }

    // [경로] 동일한 경로 즐겨 찾기가 있는지 조회
    @Override
    public Long selectSamePath(BigDecimal startLatitude, BigDecimal startLongitude, BigDecimal endLatitude, BigDecimal endLongitude, Long memberNo) {
        return queryFactory.select(pathMark.pathNo.count())
                .from(pathMark)
                .where(pathMark.pathStartLatitude.eq(startLatitude).and(pathMark.pathStartLongitude.eq(startLongitude).and(pathMark.pathEndLatitude.eq(endLatitude)
                        .and(pathMark.pathEndLongitude.eq(endLongitude).and(pathMark.memberNo.memberNo.eq(memberNo))))))
                .fetchFirst();
    }
}
