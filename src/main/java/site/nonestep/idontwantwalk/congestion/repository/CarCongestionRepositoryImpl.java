package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.entity.CarCongestion;
import site.nonestep.idontwantwalk.congestion.entity.DayType;

import java.time.LocalTime;
import java.util.List;
import static site.nonestep.idontwantwalk.congestion.entity.QCarCongestion.carCongestion1;
public class CarCongestionRepositoryImpl implements CarCongestionRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<CarCongestion> selectCarCongestion(String region, String line, String station, DayType dayType, LocalTime localTime, int dir) {
        return queryFactory.select(carCongestion1)
                .from(carCongestion1)
                .where(carCongestion1.info.region.eq(region)
                        .and(carCongestion1.info.line.eq(line))
                        .and(carCongestion1.info.station.eq(station))
                        .and(carCongestion1.carDay.eq(dayType))
                        .and(carCongestion1.carDirection.eq(dir))
                        .and(carCongestion1.carTime.eq(localTime)))
                .orderBy(carCongestion1.carIndex.asc())
                .fetch();
    }
}
