package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.dto.SubwayMarkerResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.congestion.entity.QDownCongestion.*;
import static site.nonestep.idontwantwalk.subway.entity.QInfo.*;
import static site.nonestep.idontwantwalk.congestion.entity.QUpCongestion.*;

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

    @Override
    public List<Tuple>  selectSubwayInfoAndCongestion(BigDecimal latitude, BigDecimal longitude, Long radius, DayType type) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(info.infoLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(info.infoLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(info.infoLongitude))))
                )).multiply(6371000);

        return queryFactory.select(info,upCongestion,downCongestion, distanceExpression )
                .from(info)
                .join(upCongestion)
                .on(upCongestion.info.eq(info))
                .join(downCongestion)
                .on(downCongestion.info.eq(info))
                .where(upCongestion.upCongestionType.eq(type).and(downCongestion.downCongestionType.eq(type))
                        .and(distanceExpression.loe(radius)))
                .fetch();
    }
}
