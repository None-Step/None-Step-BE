package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.InfoCharger;
import site.nonestep.idontwantwalk.subway.dto.SubwayChargerResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayDifToiletResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QCharger.*;
import static site.nonestep.idontwantwalk.subway.entity.QDifToilet.difToilet;

public class SubwayChargerRepositoryImpl implements SubwayChargerRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    // 역 내 전동 휠체어 충전 설비 조회
    @Override
    public List<SubwayChargerResponseDTO> selectCharger(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(charger.chargerLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(charger.chargerLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(charger.chargerLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayChargerResponseDTO.class, charger.info.region,
                charger.info.line, charger.info.station, charger.chargerComment, charger.chargerAddress,
                charger.chargerLatitude, charger.chargerLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(charger)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회시 필요
    @Override
    public List<InfoCharger> selectInfoCharger(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoCharger.class, charger.chargerComment))
                .from(charger)
                .where(charger.info.region.eq(region).and(charger.info.line.eq(line).and(charger.info.station.eq(station))))
                .fetch();
    }
}
