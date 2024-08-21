package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayDownTimeRequestDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayDownTimeResponseDTO;
import static site.nonestep.idontwantwalk.subway.entity.QDownTime.*;

import java.util.List;

public class SubwayDownTimeRepositoryImpl implements SubwayDownTimeRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 지역, 호선, 역 명이 같으면 시간표 전체 조회
    @Override
    public List<SubwayDownTimeResponseDTO> selectDownTime(SubwayDownTimeRequestDTO subwayDownTimeRequestDTO) {
        return queryFactory.select(Projections.constructor(SubwayDownTimeResponseDTO.class, downTime.downDirection,
                downTime.downWeekdayStart, downTime.downWeekdayEnd, downTime.downSatStart, downTime.downSatEnd,
                downTime.downHolidayStart, downTime.downHolidayEnd))
                .from(downTime)
                .where(downTime.info.region.eq(subwayDownTimeRequestDTO.getRegion()).and(downTime.info.line.eq(subwayDownTimeRequestDTO.getLine())
                        .and(downTime.info.station.eq(subwayDownTimeRequestDTO.getStation()))))
                .fetch();
    }


}
