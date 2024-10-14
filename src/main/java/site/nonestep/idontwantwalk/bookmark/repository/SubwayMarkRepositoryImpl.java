package site.nonestep.idontwantwalk.bookmark.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.bookmark.dto.SubwayDeleteResponseDTO;
import site.nonestep.idontwantwalk.bookmark.dto.SubwayListResponseDTO;

import java.util.List;
import java.util.Optional;

import static site.nonestep.idontwantwalk.bookmark.entity.QSubwayMark.*;
import static site.nonestep.idontwantwalk.member.entity.QMember.*;

public class SubwayMarkRepositoryImpl implements SubwayMarkRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // [지하철 역] 즐겨 찾기 등록 전, 갯수 초과가 아닌지 조회
    @Override
    public Long selectSubwayMarkCount(Long memberNo) {
        return queryFactory.select(subwayMark.station.count())
                .from(subwayMark)
                .where(subwayMark.memberNo.eq(memberNo))
                .fetchFirst();
    }

    // [지하철 역] 동일한 즐겨 찾기가 있는지 조회
    // Count로 조회해서 0이 아니라면 중복으로 등록하려는 경우이기 때문에 null을 return한다.
    @Override
    public Long selectSameSubway(String region, String line, String station, Long memberNo) {
        return queryFactory.select(subwayMark.station.count())
                .from(subwayMark)
                .where(subwayMark.region.eq(region).and(subwayMark.line.eq(line).and(subwayMark.station.eq(station).and(subwayMark.memberNo.eq(memberNo)))))
                .fetchFirst();
    }

    // [지하철 역] 즐겨 찾기 조회
    @Override
    public List<SubwayListResponseDTO> selectSubwayMark(Long memberNo) {
        return queryFactory.select(Projections.constructor(SubwayListResponseDTO.class, subwayMark.region,
                        subwayMark.line, subwayMark.station))
                .from(subwayMark)
                .where(subwayMark.memberNo.eq(memberNo))
                .fetch();
    }

    // [지하철 역] 즐겨 찾기 삭제
    @Override
    public void deleteSubwayMark(String region, String line, String station, Long memberNo) {
        queryFactory.delete(subwayMark)
                .where(subwayMark.region.eq(region).and(subwayMark.line.eq(line).and(subwayMark.station.eq(station)
                        .and(subwayMark.memberNo.eq(memberNo)))))
                .execute();
    }
}
