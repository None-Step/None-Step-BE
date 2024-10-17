package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;
import static site.nonestep.idontwantwalk.congestion.entity.QDownCongestion.*;

public class DownCongestionRepositoryImpl implements DownCongestionRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    @Override
    public DownCongestion selectCurrentTimeTo1Hours(String region, String line, String station, DayType type) {
        return queryFactory.select(downCongestion)
                .from(downCongestion)
                .where(downCongestion.info.region.eq(region).and(downCongestion.info.line.eq(line)
                        .and(downCongestion.info.station.eq(station).and(downCongestion.downCongestionType.eq(type)))))
                .fetchFirst();
    }
}
