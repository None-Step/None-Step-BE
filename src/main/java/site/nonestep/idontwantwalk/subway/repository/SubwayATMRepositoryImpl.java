package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.InfoAtm;
import site.nonestep.idontwantwalk.subway.dto.SubwayATMResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayDifToiletResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QDifToilet.difToilet;
import static site.nonestep.idontwantwalk.subway.entity.QAtm.*;

public class SubwayATMRepositoryImpl implements SubwayATMRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    // 역 내 atm 조회
    @Override
    public List<SubwayATMResponseDTO> selectATM(BigDecimal latitude, BigDecimal longitude, Long radius) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(atm.atmLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(atm.atmLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(atm.atmLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayATMResponseDTO.class, atm.info.region,
                atm.info.line, atm.info.station, atm.atmComment, atm.atmAddress, atm.atmLatitude,
                atm.atmLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(atm)
                .where(distanceExpression.loe(radius))
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회
    @Override
    public List<InfoAtm> selectInfoAtm(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoAtm.class, atm.atmComment))
                .from(atm)
                .where(atm.info.region.eq(region).and(atm.info.line.eq(line).and(atm.info.station.eq(station))))
                .fetch();
    }
}
