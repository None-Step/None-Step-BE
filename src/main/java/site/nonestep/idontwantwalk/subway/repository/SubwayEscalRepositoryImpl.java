package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QElevator.elevator;
import static site.nonestep.idontwantwalk.subway.entity.QEscal.*;

public class SubwayEscalRepositoryImpl implements SubwayEscalRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    // 에스컬레이터 있는 역 조회
    @Override
    public List<SubwayEscalResponseDTO> selectEscal(BigDecimal latitude, BigDecimal longitude, Long radius) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(escal.escalLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(escal.escalLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(escal.escalLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayEscalResponseDTO.class, escal.info.region,
                        escal.info.line, escal.info.station, escal.escalComment, escal.escalAddress,
                        escal.escalLatitude, escal.escalLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(escal)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }
}
