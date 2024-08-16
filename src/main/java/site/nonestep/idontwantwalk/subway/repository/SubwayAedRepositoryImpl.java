package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.aspectj.weaver.ast.Expr;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.InfoAed;
import site.nonestep.idontwantwalk.subway.dto.SubwayAEDResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayATMResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QAtm.atm;
import static site.nonestep.idontwantwalk.subway.entity.QAed.*;

public class SubwayAedRepositoryImpl implements SubwayAedRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 역 내 제세동기(AED) 조회
    @Override
    public List<SubwayAEDResponseDTO> selectAED(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(aed.aedLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(aed.aedLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(aed.aedLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayAEDResponseDTO.class, aed.info.region, aed.info.line,
                aed.info.station, aed.aedComment, aed.aedAddress, aed.aedLatitude, aed.aedLongitude,
                Expressions.as(distanceExpression, distancePath)))
                .from(aed)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회
    @Override
    public List<InfoAed> selectAed(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoAed.class, aed.aedComment))
                .from(aed)
                .where(aed.info.region.eq(region).and(aed.info.line.eq(line).and(aed.info.station.eq(station))))
                .fetch();
    }
}
