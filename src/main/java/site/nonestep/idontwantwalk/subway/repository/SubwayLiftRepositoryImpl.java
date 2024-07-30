package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayLiftResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QEscal.escal;
import static site.nonestep.idontwantwalk.subway.entity.QLift.*;

public class SubwayLiftRepositoryImpl implements SubwayLiftRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<SubwayLiftResponseDTO> selectLift(BigDecimal latitude, BigDecimal longitude, Long radius) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(lift.liftLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(lift.liftLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(lift.liftLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayLiftResponseDTO.class, lift.info.region,
                lift.info.line, lift.info.station, lift.liftExit, lift.liftAddress, lift.liftLatitude,
                lift.liftLongitude, lift.liftStartFloor, lift.liftStartComment, lift.liftEndFloor, lift.liftEndComment,
                lift.liftHeight, lift.liftWidth, lift.liftKG, Expressions.as(distanceExpression, distancePath)))
                .from(lift)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }
}