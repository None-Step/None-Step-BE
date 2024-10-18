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

    // 혼잡도 마커용 API (해당 역의 실시간 상, 하행선 혼잡도 조회)
    // join > leftJoin으로 변경함 : Info table에는 값이 있지만 다른 table에는 값이 없을 경우 그래도 출력하기 위해서
    // 그냥 join의 경우 양쪽 다 값이 있어야만 뜨는 반면, left join은 한 쪽에만 값이 있어도 뜨기 때문
    // 그래서 혼잡도가 없더라도 region, line, station, latitude, longitude가 뜨게끔 설정함
    // 조건문에는 WEEKDAY, SAT, HOLIDAY등을 하게 되면 left join을 하게 되면 info에는 있는 data지만 혼잡도가 없는 table의 경우 출력되지 않는다.
    // 만약 DayType에 값이 없더라도 출력되게끔 로직 설정 완료함.
    // .or(upCongestion.upCongestionType.isNull())), .or(downCongestion.downCongestionType.isNull())) << 해당 쿼리
    // 쿼리문 =  where ( upCongestion.type == WEEKDAY or  upCongestion.type is null )  AND ( downCongestion.type == WEEKDAY or  downCongestion.type is null );
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
                .leftJoin(upCongestion)
                .on(upCongestion.info.eq(info))
                .leftJoin(downCongestion)
                .on(downCongestion.info.eq(info))
                .where((upCongestion.upCongestionType.eq(type)
                                .or(upCongestion.upCongestionType.isNull()))
                        .and(downCongestion.downCongestionType.eq(type)
                                .or(downCongestion.downCongestionType.isNull()))
                        .and(distanceExpression.loe(radius)))
                .fetch();
    }
}
