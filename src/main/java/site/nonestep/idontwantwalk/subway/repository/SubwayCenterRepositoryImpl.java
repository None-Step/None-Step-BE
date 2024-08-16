package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.InfoCenter;
import site.nonestep.idontwantwalk.subway.dto.SubwayCenterResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayChargerResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QCenter.*;

public class SubwayCenterRepositoryImpl implements SubwayCenterRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<SubwayCenterResponseDTO> selectCenter(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(center.centerLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(center.centerLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(center.centerLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayCenterResponseDTO.class, center.info.region,
                center.info.line, center.info.station, center.centerComment, center.centerHours,
                center.centerTel, center.centerAddress, center.centerLatitude, center.centerLongitude,
                Expressions.as(distanceExpression, distancePath)))
                .from(center)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회
    @Override
    public List<InfoCenter> selectInfoCenter(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoCenter.class, center.centerComment, center.centerHours, center.centerTel))
                .from(center)
                .where(center.info.region.eq(region).and(center.info.line.eq(line).and(center.info.station.eq(station))))
                .fetch();
    }
}
