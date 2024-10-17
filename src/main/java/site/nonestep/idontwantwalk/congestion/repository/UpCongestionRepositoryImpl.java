package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.dto.UpTimeResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;

import java.util.Optional;
import static site.nonestep.idontwantwalk.congestion.entity.QUpCongestion.*;
public class UpCongestionRepositoryImpl implements UpCongestionRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤 조회
    @Override
    public UpCongestion selectCurrentTimeTo1Hours(String region, String line, String station, DayType type) {

        return queryFactory.select(upCongestion)
                .from(upCongestion)
                .where(upCongestion.info.region.eq(region).and(upCongestion.info.line.eq(line))
                        .and(upCongestion.info.station.eq(station)).and(upCongestion.upCongestionType.eq(type)))
                .fetchFirst();
    }
}
