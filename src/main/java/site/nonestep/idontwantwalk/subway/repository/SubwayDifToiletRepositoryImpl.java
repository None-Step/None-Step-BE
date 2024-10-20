package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.InfoDifToilet;
import site.nonestep.idontwantwalk.subway.dto.SubwayDifToiletResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayToiletResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QToilet.toilet;
import static site.nonestep.idontwantwalk.subway.entity.QDifToilet.*;

public class SubwayDifToiletRepositoryImpl implements SubwayDifToiletRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 역 내 장애인 화장실 조회
    @Override
    public List<SubwayDifToiletResponseDTO> selectDifToilet(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(difToilet.difToiletLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(difToilet.difToiletLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(difToilet.difToiletLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayDifToiletResponseDTO.class, difToilet.info.region,
                difToilet.info.line, difToilet.info.station, difToilet.difToiletExit, difToilet.difToiletComment,
                difToilet.difToiletAddress, difToilet.difToiletLatitude, difToilet.difToiletLongitude,
                Expressions.as(distanceExpression, distancePath)))
                .from(difToilet)
                .where(distanceExpression.loe(radius))
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회
    @Override
    public List<InfoDifToilet> selectInfoDifToilet(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoDifToilet.class, difToilet.difToiletExit, difToilet.difToiletComment))
                .from(difToilet)
                .where(difToilet.info.region.eq(region).and(difToilet.info.line.eq(line).and(difToilet.info.station.eq(station))))
                .fetch();
    }
}
