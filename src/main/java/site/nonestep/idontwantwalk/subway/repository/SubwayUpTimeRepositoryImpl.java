package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayUpTimeRequestDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayUpTimeResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.UpTime;

import java.util.List;
import static site.nonestep.idontwantwalk.subway.entity.QUpTime.*;

public class SubwayUpTimeRepositoryImpl implements SubwayUpTimeRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 지역, 호선, 역 명이 같으면 시간표 전체 조회
    @Override
    public List<SubwayUpTimeResponseDTO> selectUpTime(SubwayUpTimeRequestDTO subwayUpTimeRequestDTO) {
        return queryFactory.select(Projections.constructor(SubwayUpTimeResponseDTO.class, upTime.upDirection, upTime.upWeekdayStart,
                upTime.upWeekdayEnd, upTime.upSatStart, upTime.upSatEnd, upTime.upHolidayStart, upTime.upHolidayEnd))
                .from(upTime)
                .where(upTime.info.region.eq(subwayUpTimeRequestDTO.getRegion()).and(upTime.info.line.eq(subwayUpTimeRequestDTO.getLine())
                        .and(upTime.info.station.eq(subwayUpTimeRequestDTO.getStation()))))
                .fetch();
    }

}
