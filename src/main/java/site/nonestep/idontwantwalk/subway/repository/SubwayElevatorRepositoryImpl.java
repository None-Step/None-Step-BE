package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Elevator;
import static site.nonestep.idontwantwalk.subway.entity.QElevator.*;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;

public class SubwayElevatorRepositoryImpl implements SubwayElevatorRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 엘리베이터 있는 역 조회
    @Override
    public List<SubwayElevatorResponseDTO> selectElevator(BigDecimal latitude, BigDecimal longitude, Long radius) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(elevator.elevatorLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(elevator.elevatorLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(elevator.elevatorLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayElevatorResponseDTO.class, elevator.info.region, elevator.info.line,
                        elevator.info.station, elevator.elevatorComment, elevator.elevatorAddress, elevator.elevatorLatitude,
                        elevator.elevatorLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(elevator)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }
}
