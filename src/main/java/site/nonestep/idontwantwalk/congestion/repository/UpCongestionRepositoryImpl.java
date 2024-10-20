package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.dto.UpTimeResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;

import java.util.Optional;
import static site.nonestep.idontwantwalk.congestion.entity.QUpCongestion.*;
import static site.nonestep.idontwantwalk.subway.entity.QInfo.*;
public class UpCongestionRepositoryImpl implements UpCongestionRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤 조회
    // downCongestion table에는 없는 역들이 존재하기 때문에 right join 한다.
    // 정보가 없는 역들은 null로 보낸다.
    @Override
    public UpCongestion selectCurrentTimeTo1Hours(String region, String line, String station, DayType type) {

        return queryFactory.select(upCongestion)
                .from(upCongestion)
                .rightJoin(info)
                .on(info.eq(upCongestion.info))
                .where(info.region.eq(region).and(info.line.eq(line))
                        .and(info.station.eq(station)).and(upCongestion.upCongestionType.eq(type)
                                .or(upCongestion.upCongestionType.isNull())))
                .fetchFirst();
    }
}
